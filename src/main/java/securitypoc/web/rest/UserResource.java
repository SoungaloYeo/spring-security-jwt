package securitypoc.web.rest;

import javax.servlet.http.HttpServletRequest;

import securitypoc.config.Constants;
import securitypoc.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import securitypoc.service.dto.UserDataDTO;
import securitypoc.service.dto.UserResponseDTO;
import securitypoc.domain.User;


@RestController
@RequestMapping(Constants.API_USER_URI)
@Api(tags = "users")
public class UserResource {

  @Autowired
  private UserService userService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping("/signin")
  @ApiOperation(value = "${UserResource.getToken}")
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 422, message = "Invalid username/password supplied")})
  public String login(//
      @ApiParam("Username") @RequestParam String username, //
      @ApiParam("Password") @RequestParam String password) {
    return userService.getToken(username, password);
  }

  @PostMapping("/signup")
  @ApiOperation(value = "${UserResource.create}")
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 403, message = "Access denied"), //
      @ApiResponse(code = 422, message = "Username is already in use")})
  public String create(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
    return userService.createUser(modelMapper.map(user, User.class));
  }

  @PostMapping("/change")
  @ApiOperation(value = "${UserResource.updateUserRoles}", authorizations = { @Authorization(value="apiKey") })
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 403, message = "Access denied"), //
      @ApiResponse(code = 404, message = "Username not exist")})
  @PreAuthorize("hasRole(\""+ Constants.ROLE_ADMIN+"\")")
  public String updateUserRoles(@ApiParam("update User roles") @RequestBody UserDataDTO user) {
    return userService.updateUserRoles(modelMapper.map(user, User.class));
  }

  @DeleteMapping(value = "/{username}")
  @PreAuthorize("hasRole(\""+ Constants.ROLE_ADMIN+"\")")
  @ApiOperation(value = "${UserResource.delete}", authorizations = { @Authorization(value="apiKey") })
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 403, message = "Access denied"), //
      @ApiResponse(code = 404, message = "The user doesn't exist"), //
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public String delete(@ApiParam("Username") @PathVariable String username) {
    userService.deleteUser(username);
    return username;
  }

  @GetMapping(value = "/{username}")
  @PreAuthorize("hasRole(\""+ Constants.ROLE_ADMIN+"\")")
  @ApiOperation(value = "${UserResource.getUserByUsername}", response = UserResponseDTO.class, authorizations = { @Authorization(value="apiKey") })
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 403, message = "Access denied"), //
      @ApiResponse(code = 404, message = "The user doesn't exist"), //
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public UserResponseDTO getUser(@ApiParam("Username") @PathVariable String username) {
    return modelMapper.map(userService.getUserByUsername(username), UserResponseDTO.class);
  }

  @GetMapping(value = "/info")
  @PreAuthorize("hasRole(\""+ Constants.ROLE_ADMIN+"\") or hasRole(\"" + Constants.ROLE_USER + "\")")
  @ApiOperation(value = "${UserResource.getUserInfo}", response = UserResponseDTO.class, authorizations = { @Authorization(value="apiKey") })
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 403, message = "Access denied"), //
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public UserResponseDTO getUserInfo(HttpServletRequest req) {
    return modelMapper.map(userService.getUserInfo(req), UserResponseDTO.class);
  }

  @GetMapping("/refresh")
  @PreAuthorize("hasRole(\""+ Constants.ROLE_ADMIN+"\") or hasRole(\"" + Constants.ROLE_USER + "\")")
  public String refresh(HttpServletRequest req) {
    return userService.refreshToken(req.getRemoteUser());
  }

}
