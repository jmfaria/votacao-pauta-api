package api.response;

import java.util.ArrayList;
import java.util.List;

import api.exception.ApiExceptionHandler.Erro;

public class Response<T> {

	private T data;
	private List<Erro> errors;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<Erro> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<>();
		}
		return errors;
	}

	public void setErrors(List<Erro> errors) {
		this.errors = errors;
	}

}