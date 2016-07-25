package de.mybambergapp.manager;

import de.mybambergapp.dto.RouteDTO;
import de.mybambergapp.dto.UserDTO;

/**
 * Created by christian on 23.07.16.
 */
public interface Repository {

     void saveUser(UserDTO userDTO);
     UserDTO getUser();
     void  saveRouteDTO(RouteDTO routeDTO);
     RouteDTO  getRouteDTO();
     void addEventToRoute(RouteDTO routeDTO);
     void  removeEventFromRoute(RouteDTO routeDTO);


}
