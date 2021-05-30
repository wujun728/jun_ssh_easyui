package com.lostad.app.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.assertj.core.internal.Strings;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 
 * @author songsz
 *
 */
@Controller
public class CommonErrorController implements ErrorController {

	private static final String ERROR_PATH = "/error";

	@RequestMapping(ERROR_PATH)
	public String handleError() {
		return "common/error";
	}

	
	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}