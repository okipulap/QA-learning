package apiTests.base;

import apiTests.models.PetRequest;
import apiTests.models.PetResponse;
import apiTests.specs.RequestSpec;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class PetClient extends ApiBaseClient {
    public PetClient() {
        super(RequestSpec.defaultSpec());
    }

    public PetResponse createPet(PetRequest request) {
        return create("/pet", request)
                .then()
                .log().ifError()
                .extract()
                .as(PetResponse.class);
    }

    public PetResponse putPet(PetRequest request, Long id) {
        return put("/pet", request, id)
                .then()
                .log().ifError()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(PetResponse.class);
    }

}
