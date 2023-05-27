package jp.co.axa.apidemo.controllers;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class Response<T> {

	private LinkedHashMap<String, Object> meta;
	private List<T> items;

	/** 0 or 1 item as response totalRecord is zero if no record found */
	public static <T> Response<T> newResponse(T item) {
		Response<T> res = new Response<>();
		if (Objects.isNull(item)) {
			res.setMeta(newMeta(0, 0, 0L));
			return res;
		}
		res.setMeta(newMeta(0, 1, 1L));
		res.setItems(Arrays.asList(item));
		return res;
	}

	/**
	 * Response that has N items, Where limit is N, TotalReccords could be greater
	 * than N
	 */
	public static <T> Response<T> newResponse(List<T> items, int offset, int limit, long totalRecords) {
		Response<T> res = new Response<>();
		res.setMeta(newMeta(offset, limit, totalRecords));
		res.setItems(items);
		return res;
	}

	private static LinkedHashMap<String, Object> newMeta(int offset, int limit, long totalRecords) {
		LinkedHashMap<String, Object> meta = new LinkedHashMap<>();
		meta.put("offset", offset);
		meta.put("limit", limit);
		meta.put("totalRecords", totalRecords);
		return meta;
	}
}
