package com.assignment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.objects.ResponseObject;
import com.assignment.service.WithdrawService;

@RestController
@RequestMapping("/user")
public class ATMController {
	
	private static final Logger logger = LoggerFactory.getLogger(ATMController.class);
	
	@Autowired
	WithdrawService withdrawService;

	@RequestMapping(value = "/withdraw/{amount}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseObject> withdraw(@PathVariable("amount") int withdrawAmount) {
		ResponseObject response = new ResponseObject();
		try {
			response = withdrawService.withdrawAmount(withdrawAmount);
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			response.setSuccess(Boolean.FALSE);
			response.setErrorMessage(e.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
