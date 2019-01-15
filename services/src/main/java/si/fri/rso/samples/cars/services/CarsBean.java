package si.fri.rso.samples.cars.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.samples.cars.entities.Car;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class CarsBean {

    private Logger log = Logger.getLogger(CarsBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Car> getCar(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Car.class, queryParameters);

    }
    public List<Car> getCar() {

        TypedQuery<Car> query = em.createNamedQuery("Car.getAll", Car.class);

        return query.getResultList();

    }

    public Car getCar(Integer carId) {

        Car car = em.find(Car.class, carId);

        if (car == null) {
            throw new NotFoundException();
        }

        return car;
    }

    public Car createCar(Car car) {

        try {
            beginTx();
            em.persist(car);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return car;
    }

    public Car putCar(Integer carId, Car car) {

        Car c = em.find(Car.class, carId);

        if (c == null) {
            return null;
        }

        try {
            beginTx();
            car.setId(c.getId());
            car = em.merge(car);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return car;
    }


    public boolean deleteCar(String carId) {

        Car car = em.find(Car.class, carId);

        if (car != null) {
            try {
                beginTx();
                em.remove(car);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else
            return false;

        return true;
    }



    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
