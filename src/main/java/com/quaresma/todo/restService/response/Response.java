package com.quaresma.todo.restService.response;

import java.util.ArrayList;
import java.util.List;

/**Esta classe trabalha com as respostas e dos verbos HTTP (GET, POST, PUT, DELETE)*/
public class Response<T> {

	private T data;

	private List<String> errors;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<String>();
		}
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
