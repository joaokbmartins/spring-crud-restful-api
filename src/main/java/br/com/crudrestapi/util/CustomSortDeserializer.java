package br.com.crudrestapi.util;

import java.io.IOException;

import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class CustomSortDeserializer extends JsonDeserializer<Sort> {

    public Sort deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

	ArrayNode node = jsonParser.getCodec().readTree(jsonParser);
//	ArrayNode node = ((ObjectMapper) jsonParser.getCodec()).getNodeFactory().arrayNode();

	System.out.println("node.size(): " + node.size());
	Sort.Order[] orders = new Sort.Order[node.size()];

	int counter = 0;
	for (JsonNode json : node) {
	    System.out.println("Aqui !!! LLL");
	    orders[counter] = new Sort.Order(Sort.Direction.valueOf(json.get("direction").asText()),
		    json.get("property").asText());
	    counter++;
	}
	return Sort.by(orders);
    }

}
