package br.com.crudrestapi.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomPageableResponse<T> extends PageImpl<T> {

    private static final long serialVersionUID = 3248189030448292002L;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CustomPageableResponse(
	    @JsonProperty("content") List<T> content,
	    @JsonProperty("size") int size,
	    @JsonProperty("number") int number,
	    @JsonProperty("totalElements") long totalElements,
//	    @JsonProperty("sort") @JsonDeserialize(using = CustomSortDeserializer.class) Sort sort,
	    @JsonProperty("pageable") JsonNode pageable,
	    @JsonProperty("last") boolean last,
	    @JsonProperty("totalPages") int totalPages,
	    @JsonProperty("first") boolean first,
	    @JsonProperty("numberOfElements") int numberOfElements) {
	super(content, PageRequest.of(number, size), totalElements);
    }

//    public CustomPageableResponse(List<T> content, Pageable pageable, long total) {
//	super(content, pageable, total);
//    }

    public CustomPageableResponse(List<T> content) {
	super(content);
    }

    public CustomPageableResponse() {
	super(new ArrayList<T>());
    }

}