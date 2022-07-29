package co.com.poli.bookingservice.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuild {

    public Response success(Object data){
        return Response.builder()
                .data(data)
                .code(HttpStatus.OK.value()).build();
    }

    public Response createSuccess(Object data){
        return Response.builder()
                .data(data)
                .code(HttpStatus.CREATED.value()).build();
    }

    public Response notFound(){
        return Response.builder()
                .data(HttpStatus.NOT_FOUND)
                .code(HttpStatus.NOT_FOUND.value()).build();
    }

    public Response badRequest(Object data){
        return Response.builder()
                .data(data)
                .code(HttpStatus.BAD_REQUEST.value()).build();
    }
}
