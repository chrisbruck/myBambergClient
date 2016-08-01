package de.mybambergapp.manager;

import android.content.Context;

import de.mybambergapp.dto.RouteDTO;
import de.mybambergapp.dto.UserDTO;

/**
 * Created by christian on 23.07.16.
 */
public interface Repository {


     void  saveRouteDTO(RouteDTO routeDTO, Context context);
     RouteDTO  getRouteDTO(Context context);
     void addEventToRoute(RouteDTO routeDTO);
     void  removeEventFromRoute(RouteDTO routeDTO);


}
