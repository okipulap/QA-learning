package apiTests.models.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {

    @JsonProperty("approved")
    private int approved;

    @JsonProperty("placed")
    private int placed;

    @JsonProperty("delivered")
    private int delivered;
}