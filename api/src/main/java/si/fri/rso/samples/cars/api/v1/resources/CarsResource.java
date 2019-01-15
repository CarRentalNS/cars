package si.fri.rso.samples.cars.api.v1.resources;

import si.fri.rso.samples.cars.entities.Car;
import si.fri.rso.samples.cars.services.CarsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("/cars")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarsResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private CarsBean carsBean;

    @GET
    public Response getCars() {

        List<Car> cars = carsBean.getCar(uriInfo);

        return Response.ok(cars).build();
    }

    @GET
    @Path("/{carId}")
    public Response getCar(@PathParam("carId") Integer carId) {

        Car car = carsBean.getCar(carId);

        if (car == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(car).build();
    }

    @POST
    public Response createCar(Car car) {

       if ((car.getCarLocation() == null || car.getCarLocation().isEmpty())||(car.getCarModel()==null || car.getCarModel().isEmpty()) || (car.getCarStatus()== null || car.getCarStatus().isEmpty())){
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            car = carsBean.createCar(car);
        }

        if (car.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(car).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(car).build();
        }
    }

    @PUT
    @Path("{carId}")
    public Response putCar(@PathParam("carId") Integer carId, Car car) {

        car = carsBean.putCar(carId, car);

        if (car == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (car.getId() != null)
                return Response.status(Response.Status.OK).entity(car).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("{carId}")
    public Response deleteCustomer(@PathParam("carId") String carId) {

        boolean deleted = carsBean.deleteCar(carId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
