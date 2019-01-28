package com.iig.gcp.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.iig.gcp.CustomAuthenticationProvider;
import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.RunFeeds1;
import com.iig.gcp.login.dto.UserAccount;
import com.iig.gcp.login.service.LoginService;



@Controller
@SessionAttributes(value= {"user","arrProject","menu_code","project","projectFeatureMap"})
public class LoginController {

	
	@Autowired
    private CustomAuthenticationProvider authenticationManager;
	
	@Value( "${oracle.front.micro.services}" )
	private String oracle_front_micro_services;
	
	@Value( "${unix.front.micro.services}" )
	private String unix_front_micro_services;
	
	@Value( "${admin.front.micro.services}" )
	private String admin_front_micro_services;
	
	@Value( "${hip.front.micro.services}" )
	private String hip_front_micro_services;
	
	@Value( "${hive.front.micro.services}" )
	private String hive_front_micro_services;
	
	
	@Value( "${propagation.front.micro.services}" )
	private String propagation_front_micro_services;
	
	@Value( "${schedular.front.micro.services}" )
	private String schedular_front_micro_services;
	
	@Value( "${smartarchival.front.micro.services}" )
	private String smartarchival_front_micro_services;
	
	@Value( "${teradata.front.micro.services}" )
	private String teradata_front_micro_services;
	
	private static String oracle_pwd;
	@Value("${oracle.encrypt.pwd}")
	public void setPassword(String value) {
		this.oracle_pwd=value;
	}
	
	@Autowired
	LoginService loginService;

	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
	public String homePage() throws Exception {
		
		return "/index";
	}

	@RequestMapping(value = { "/login"}, method = RequestMethod.GET)
	public String login() {

		return "/login/login";
	}
	
	@RequestMapping(value = { "/features"}, method = RequestMethod.GET)
	public String features() {

		return "/features";
	}


	@RequestMapping(value = {"/accessDenied" })
	public String unAuthorizedPage() {
		return "accessDenied";
	}

	
	@RequestMapping(value = { "/fromChild"}, method = RequestMethod.GET)
	public ModelAndView unixExtractionHome(@Valid @ModelAttribute("jsonObject") String jsonObject, ModelMap modelMap,HttpServletRequest request) throws Exception {
		
		//Validate the token at the first place
		JSONObject jsonModelObject = null;
		try {
		
		if(jsonObject == null || jsonObject.equals("")) {
			//TODO: Redirect to Access Denied Page
			return new ModelAndView("/login");
		}
		jsonModelObject = new JSONObject( jsonObject);
		System.out.println(jsonModelObject.get("userId"));
		System.out.println(jsonModelObject.get("project"));
		System.out.println("Child to Parent Token" + jsonModelObject.get("jwt").toString());
		authenticationByJWT(jsonModelObject.get("userId").toString()+":"+jsonModelObject.get("project").toString(), jsonModelObject.get("jwt").toString());
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ModelAndView("/login");
			//redirect to Login Page
		}
		
		request.getSession().setAttribute("project", jsonModelObject.get("project"));
		
		modelMap.addAttribute("user_id", jsonModelObject.get("userId"));
		
		boolean flag=false;
		UserAccount user=null;
		try {
			ArrayList<UserAccount> arrUserAccount= loginService.getUserAccount();
			for(int i=0;i<arrUserAccount.size();i++) {
				user=arrUserAccount.get(i);
				if(user.getUser_id().equals(jsonModelObject.get("userId"))) {
					/*if(user.getUser_pass().equals(password)) {

				}*/
					flag=true;
					break;

				}
			}

			if(!flag) {
				modelMap.addAttribute("errorString","Please Raise GSR to Access Juniper");
				return new ModelAndView("login/login");
			}else {
				ArrayList<Project> arrProject = loginService.getProjects(jsonModelObject.get("userId").toString());
				modelMap.addAttribute("arrProject",arrProject);
				modelMap.addAttribute("user",user);
				/*HashMap<String,Integer> hsmap=new HashMap<String,Integer>();
				for(Project project:arrProject ) {
					hsmap.put(project.getProject_id(), project.getProject_sequence());
				}
				
				modelMap.addAttribute("projectFeatureMap", hsmap);
				String menu_code=loginService.getJAdminMenuCodes(user.getUser_sequence());
				if(!menu_code.isEmpty()||!menu_code.contains("")){
					modelMap.addAttribute("menu_code",menu_code);	
				}*/
			}

		}catch(Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorString",e.getMessage());
			return new ModelAndView("login/login");
		}
		//return new ModelAndView("cdg_home");
		request.getSession().setAttribute("user", user);
		ArrayList<RunFeeds1> feeds = loginService.getProjectFeeds((String)request.getSession().getAttribute("project"));
		ArrayList<RunFeeds1> users = loginService.getProjectUsers((String)request.getSession().getAttribute("project"));
		ArrayList<RunFeeds1> cruns = loginService.getCurrentRuns((String)request.getSession().getAttribute("project"));
		ArrayList<RunFeeds1> lruns = loginService.getLastRuns((String)request.getSession().getAttribute("project"));
		ArrayList<RunFeeds1> uruns = loginService.getUpcomingRuns((String)request.getSession().getAttribute("project"));
		ArrayList<RunFeeds1> fruns = loginService.getFailedRuns((String)request.getSession().getAttribute("project"));
		modelMap.addAttribute("feeds", feeds);
		modelMap.addAttribute("users", users);
		modelMap.addAttribute("cruns", cruns);
		modelMap.addAttribute("lruns", lruns);
		modelMap.addAttribute("uruns", uruns);
		modelMap.addAttribute("fruns", fruns);
		int feedrunning=cruns.size(), userno=users.size() , feedno=feeds.size();
		modelMap.addAttribute("userno",userno);
		modelMap.addAttribute("feedno",feedno);
		modelMap.addAttribute("feedrunning",feedrunning);
		
		
		String menu_code=null;
		try {
			//UserAccount user = (UserAccount)request.getSession().getAttribute("user");
			menu_code=loginService.getMenuCodes(user.getUser_sequence(),(String)request.getSession().getAttribute("project"));
			menu_code=menu_code.replaceAll("\\$\\{user.user_id\\}", user.getUser_id());
			menu_code=menu_code.replaceAll("\\$\\{project\\}", (String)request.getSession().getAttribute("project"));
			menu_code=menu_code.replaceAll("\\$\\{jwt\\}", jsonModelObject.get("jwt").toString());
			modelMap.addAttribute("menu_code",menu_code);
			modelMap.addAttribute("project",(String)request.getSession().getAttribute("project"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("cdg_home");
		
		
		//return new ModelAndView("projectDashboard");
	}
	
	
	private void authenticationByJWT(String name, String token) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(name, token);
        Authentication authenticate = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
	}
	
	
	@RequestMapping(value = { "/login/dashboard"}, method = RequestMethod.GET)
	public String helpPage(String project,ModelMap modelMap,HttpServletRequest request) throws Exception {
		
		ArrayList<RunFeeds1> feeds = loginService.getProjectFeeds((String)request.getSession().getAttribute("project"));
		ArrayList<RunFeeds1> users = loginService.getProjectUsers((String)request.getSession().getAttribute("project"));
		ArrayList<RunFeeds1> cruns = loginService.getCurrentRuns((String)request.getSession().getAttribute("project"));
		ArrayList<RunFeeds1> lruns = loginService.getLastRuns((String)request.getSession().getAttribute("project"));
		ArrayList<RunFeeds1> uruns = loginService.getUpcomingRuns((String)request.getSession().getAttribute("project"));
		ArrayList<RunFeeds1> fruns = loginService.getFailedRuns((String)request.getSession().getAttribute("project"));
		modelMap.addAttribute("feeds", feeds);
		modelMap.addAttribute("users", users);
		modelMap.addAttribute("cruns", cruns);
		modelMap.addAttribute("lruns", lruns);
		modelMap.addAttribute("uruns", uruns);
		modelMap.addAttribute("fruns", fruns);
		int feedrunning=cruns.size(), userno=users.size() , feedno=feeds.size();
		modelMap.addAttribute("userno",userno);
		modelMap.addAttribute("feedno",feedno);
		modelMap.addAttribute("feedrunning",feedrunning);
		return "projectDashboard";
	}

	@RequestMapping(value = { "/login/submit"}, method = RequestMethod.GET)
	public ModelAndView authenticateUser(String username,String password,ModelMap modelMap , Principal principal ) {
		boolean flag=false;
		username = principal.getName();
		UserAccount user=null;
		try {
			ArrayList<UserAccount> arrUserAccount= loginService.getUserAccount();
			for(int i=0;i<arrUserAccount.size();i++) {
				user=arrUserAccount.get(i);
				if(user.getUser_id().equals(username)) {
					/*if(user.getUser_pass().equals(password)) {

				}*/
					flag=true;
					break;

				}
			}


			if(!flag) {
				modelMap.addAttribute("errorString","Please Raise GSR to Access Juniper");
				return new ModelAndView("login/login");
			}else {
				ArrayList<Project> arrProject = loginService.getProjects(username);
				modelMap.addAttribute("arrProject",arrProject);
				modelMap.addAttribute("user",user);
				/*HashMap<String,Integer> hsmap=new HashMap<String,Integer>();
				for(Project project:arrProject ) {
					hsmap.put(project.getProject_id(), project.getProject_sequence());
				}*/
				
				/*modelMap.addAttribute("projectFeatureMap", hsmap);
				String menu_code=loginService.getJAdminMenuCodes(user.getUser_sequence());
				if(!menu_code.isEmpty()||!menu_code.contains("")){
					modelMap.addAttribute("menu_code",menu_code);	
				}*/
			}

		}catch(Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorString",e.getMessage());
			return new ModelAndView("login/login");
		}
		return new ModelAndView("cdg_home");
	}

	/**
	 * 
	 * @param project
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/login/features"}, method = RequestMethod.POST)
	public ModelAndView getFeatures(String project,String jwt,ModelMap modelMap ,HttpServletRequest request) {
		String menu_code=null;
		try {
			UserAccount user = (UserAccount)request.getSession().getAttribute("user");
			menu_code=loginService.getMenuCodes(user.getUser_sequence(),project);
			menu_code=menu_code.replaceAll("\\$\\{user.user_id\\}", user.getUser_id());
			menu_code=menu_code.replaceAll("\\$\\{project\\}", project);
			menu_code=menu_code.replaceAll("\\$\\{jwt\\}", jwt);
			modelMap.addAttribute("menu_code",menu_code);
			modelMap.addAttribute("project",project);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("cdg_home");
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/logout"}, method = RequestMethod.POST)
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "/login/login";
	}
	
	
	@RequestMapping(value = { "/login/extractionMS"}, method = RequestMethod.GET)
	public ModelAndView  extractionMS(@RequestParam String user,@RequestParam String project,@RequestParam String jwt,ModelMap modelMap ) throws IOException {
		
		modelMap.addAttribute("usr",user.toString());
		modelMap.addAttribute("proj",project);
		modelMap.addAttribute("jwt",jwt);
		return new ModelAndView("ConnectionHome");
	}
	/*
	 * Micro-service call for DATA EXTRACTION
	 */
	@RequestMapping(value = { "/login/connectionDetails"}, method = RequestMethod.POST)
	public ModelAndView  connectionDetails(@Valid @ModelAttribute("src_val") String src_val,@Valid @ModelAttribute("usr") String userId,@Valid @ModelAttribute("proj") String project,@Valid @ModelAttribute("jwt") String jwt, ModelMap modelMap,HttpServletRequest request) throws ClassNotFoundException, SQLException, Exception {
		UserAccount user = (UserAccount)request.getSession().getAttribute("user");
		String menu_code=loginService.getMenuCodes(user.getUser_sequence(),project);
		menu_code=menu_code.replaceAll("\\$\\{user.user_id\\}", user.getUser_id());
		menu_code=menu_code.replaceAll("\\$\\{project\\}", project);
		menu_code=menu_code.replaceAll("\\$\\{jwt\\}", jwt);
		modelMap.addAttribute("menu_code",menu_code);
		modelMap.addAttribute("project",project);
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("userId", userId.toString());
		jsonObject.put("project", project);
		jsonObject.put("jwt", jwt);
		modelMap.addAttribute("jsonObject",jsonObject.toString());
		if(src_val.equalsIgnoreCase("oracle"))
		{
			return new ModelAndView("redirect://"+ oracle_front_micro_services ,modelMap);
		}
		else if(src_val.equalsIgnoreCase("Unix"))
		{
			return new ModelAndView("redirect://"+ unix_front_micro_services,modelMap);
		} 
		else if(src_val.equalsIgnoreCase("Hive"))
		{
			return new ModelAndView("redirect://"+ hive_front_micro_services,modelMap);
		} else if(src_val.equalsIgnoreCase("Teradata"))
		{
			return new ModelAndView("redirect://"+ teradata_front_micro_services,modelMap);
		} else
			return new ModelAndView("redirect://localhost:5774",modelMap);
	}
	/*
	 * Micro-service call for HIP Dashboard
	 */
	@RequestMapping(value = { "/login/hipMS"}, method = RequestMethod.GET)
	public ModelAndView hipMS( ModelMap modelMap ,HttpServletResponse response) throws IOException, JSONException {
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("micro", "service");
		modelMap.addAttribute("jsonObject",jsonObject.toString());
		return new ModelAndView("redirect:" + "//"+ hip_front_micro_services, modelMap);
	}
	/*
	 * Micro-service call for ADMIN PORTAL
	 */
	@RequestMapping(value = { "/login/adminMS"}, method = RequestMethod.GET)
	public ModelAndView adminMS(@RequestParam String user,@RequestParam String project,@RequestParam String jwt, ModelMap modelMap ,HttpServletResponse response) throws IOException, JSONException {
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("userId", user);
		jsonObject.put("project", project);
		jsonObject.put("jwt", jwt);
		modelMap.addAttribute("jsonObject",jsonObject.toString());
		return new ModelAndView("redirect:" + "//"+ admin_front_micro_services, modelMap);
	}
	/*
	 * Micro-service call for SCHEDULAR
	 */
	@RequestMapping(value = { "/login/schedulerMS"}, method = RequestMethod.GET)
	public ModelAndView schedulerMS(@RequestParam String user,@RequestParam String project,@RequestParam String jwt, ModelMap modelMap ,HttpServletResponse response) throws IOException, JSONException {
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("user", user);
		jsonObject.put("project", project);
		jsonObject.put("jwt", jwt);
		modelMap.addAttribute("jsonObject",jsonObject.toString());
		return new ModelAndView("redirect:" + "//"+ schedular_front_micro_services, modelMap);
	}
	/*
	 * Micro-service call for HIP REGISTER
	 */
	@RequestMapping(value = { "/login/hipMS/register"}, method = RequestMethod.GET)
	public ModelAndView hipMSRegister( ModelMap modelMap ,HttpServletResponse response) throws IOException, JSONException {
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("micro", "service");
		modelMap.addAttribute("jsonObject",jsonObject.toString());
		return new ModelAndView("redirect:" + "//"+ hip_front_micro_services+"/register", modelMap);
	}
	/*
	 * Micro-service call for DATA PROPAGATION
	 */
	@RequestMapping(value = { "/login/datapropagationMS"}, method = RequestMethod.GET)
	public ModelAndView datapropagationMS(@RequestParam String user,@RequestParam String project,@RequestParam String jwt, ModelMap modelMap ,HttpServletResponse response) throws IOException, JSONException {
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("user", user);
		jsonObject.put("project", project);
		jsonObject.put("jwt", jwt);
		modelMap.addAttribute("jsonObject",jsonObject.toString());
		return new ModelAndView("redirect:" + "//"+ propagation_front_micro_services, modelMap);
	}
	/*
	 * Micro-service call for SMART ARCHIVAL
	 */
	@RequestMapping(value = { "/login/smartArchivalMS"}, method = RequestMethod.GET)
	public ModelAndView smartArchivalMS(@RequestParam String user,@RequestParam String project,@RequestParam String jwt, ModelMap modelMap ,HttpServletResponse response) throws IOException, JSONException {
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("userId", user);
		jsonObject.put("project", project);
		jsonObject.put("jwt", jwt);
		modelMap.addAttribute("jsonObject",jsonObject.toString());
		return new ModelAndView("redirect:" + "//"+ smartarchival_front_micro_services, modelMap);
	}
}