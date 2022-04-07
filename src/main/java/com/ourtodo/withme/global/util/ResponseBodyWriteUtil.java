package com.ourtodo.withme.global.util;

import static org.springframework.http.MediaType.*;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ourtodo.withme.global.dto.BaseResponse;

public class ResponseBodyWriteUtil {
	public static <T extends BaseResponse> void sendApiResponse(HttpServletResponse response, T apiResponse) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream().write(new ObjectMapper().writeValueAsString(apiResponse).getBytes());
    }
}
