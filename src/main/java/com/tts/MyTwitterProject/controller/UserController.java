package com.tts.MyTwitterProject.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.MyTwitterProject.model.Tweet;
import com.tts.MyTwitterProject.service.TweetService;
import com.tts.MyTwitterProject.service.UserService;

import antlr.collections.List;


@Controller
public class UserController {
	    
	    @Autowired
	    private UserService userService;
	    
	    @Autowired
	    private TweetService tweetService;
	   
	    @GetMapping(value = "/users/{username}")
		public String getUser(@PathVariable String username, Model model) {
		User user = userService.findByUsername(username);
		List<TweetDisplay> tweets = tweetService.findAllByUser(user);
		
		user loggedInUser = userService.getloggedInUser();
		List<User> following = loggedInUser.getFollowing();
			boolean isFollowing = false;
			
			for (User followedUser : following) {
				if (followedUser.getUsername().equals(username)) {
					isFollowing = true;
				}
			}
			
			boolean isSelfPage = loggedInUser.getUsername().equals(username);
			
			model.addAttribute("tweetList", tweets);
			model.addAttribute("user", user);
			model.addAttribute("following", isFollowing);
			model.addAttribute("isSelfPage", isSelfPage);
			return "user";
		}

		@GetMapping(value = "/users")
		public String getUsers(@RequestParam(value = "filter", required = false) String filter, Model model) {
			
			List<User> users = new ArrayList<User>();
			User loggedInUser = userService.getLoggedInUser();
			
			List<User> usersFollowing = loggedInUser.getFollowing();
			List<User> usersFollowing = loggedInUser.getFollowers();
			
			if(filter == null)
				filter == "all";
		}
		
		if (filter.equalsIgnoranceCase("follows")) {
			users = userFollowers;
		    model.addAttribute("filter", "following");
		    
		}else {
			users = userService.findAll();
			model.addAttribute("filter", "all");
		
		}
			
		model.addAttribute("users", users);
		
			SetTweetCount(users, model);
            SetFollowingStatus(users, usersfollowing, model);
            return "users";
		}

		private void SetFollowing(List<User> users, List<User> usersFollowing, Model model) {
			HashMap<String, Boolean> followingStatus = new HashMap<>();
			String username = userService.getLoggedInUser().getUsername();
			for (User user : users) {
				if (userFollowing.contains(user)) {
					followingStatus.put(user.getUsername(), true);
		
				}else{ if (!user.getUsername().equals(username)) {
				followingStatus.put(user.getUsername(), false);
				}
				
				}

		private void SetFollowingStatus(List<User> users, Model model) {
			HashMap<String, Integer> tweetCounts = new HashMap<>();
	         for (User user : users) {
				 List<TweetDisplay> tweets = tweetService.findAllByUser(user);
				 tweetCounts.put(user.getUsername(), tweets.size(();
				}
	         
			model.addAttribute("followingStatus", followingStatus);
		}



