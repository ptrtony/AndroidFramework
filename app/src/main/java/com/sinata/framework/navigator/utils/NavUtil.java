package com.sinata.framework.navigator.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.Navigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.DialogFragmentNavigator;
import androidx.navigation.fragment.FragmentNavigator;

import com.alibaba.fastjson.JSON;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.reflect.TypeToken;
import com.sinata.framework.R;
import com.sinata.framework.navigator.mode.BottomBar;
import com.sinata.framework.navigator.mode.Destination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class NavUtil {
    private static HashMap<String, Destination> destinations;
    public static String parseAssetsFile(Context context,String fileName) {
        AssetManager assets = context.getAssets();
        try {
            InputStream inputStream = assets.open("destination.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            StringBuilder stringBuffer = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            bufferedReader.close();
            inputStream.close();

            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void buildNavGraph(FragmentActivity activity, FragmentManager childrenFragmentManager, NavController controller, int containerId){
        String content = parseAssetsFile(activity,"destination.json");
        destinations = JSON.parseObject(content, new TypeToken<HashMap<String, Destination>>() {
        }.getType());
        Iterator<Destination> iterator = destinations.values().iterator();
        NavigatorProvider provider = controller.getNavigatorProvider();
        NavGraphNavigator navGraphNavigator = provider.getNavigator(NavGraphNavigator.class);
        NavGraph navGraph = new NavGraph(navGraphNavigator);
        while (iterator.hasNext()){
            Destination destination = iterator.next();
            if (destination.destType.equals("activity")){
                ActivityNavigator activityNavigator = provider.getNavigator(ActivityNavigator.class);
                ActivityNavigator.Destination node = activityNavigator.createDestination();
                node.setId(destination.id);
                node.setComponentName(new ComponentName(activity.getPackageName(),destination.clazzName));
                navGraph.addDestination(node);
            }else if (destination.destType.equals("fragment")){
                HiFragmentNavigator hiFragmentNavigator = new HiFragmentNavigator(activity,childrenFragmentManager,containerId);
//                FragmentNavigator fragmentNavigator = provider.getNavigator(FragmentNavigator.class);

                HiFragmentNavigator.Destination node = hiFragmentNavigator.createDestination();
                node.setId(destination.id);
                node.setClassName(destination.clazzName);
                navGraph.addDestination(node);
            }else if (destination.destType.equals("dialog")){
                DialogFragmentNavigator dialogFragmentNavigator = provider.getNavigator(DialogFragmentNavigator.class);
                DialogFragmentNavigator.Destination node = dialogFragmentNavigator.createDestination();
                node.setId(destination.id);
                node.setClassName(destination.clazzName);
                navGraph.addDestination(node);
            }

            if (destination.asStarter){
                navGraph.setStartDestination(destination.id);
            }
        }

        controller.setGraph(navGraph);
    }


    public static void builderBottomBar(BottomNavigationView navView){
        String content = parseAssetsFile(navView.getContext(), "main_tabs_config.json");
        BottomBar bottomBar = JSON.parseObject(content, BottomBar.class);
        List<BottomBar.Tab> tabs = bottomBar.tabs;
        Menu menu = navView.getMenu();
        for (BottomBar.Tab tab : tabs){
            if (!tab.enable) continue;
            Destination destination = destinations.get(tab.pageUrl);
            MenuItem menuItem = menu.add(0, destination.id, tab.index, tab.title);
            menuItem.setIcon(R.drawable.ic_home_black_24dp);
        }
    }
}
