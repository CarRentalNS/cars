package si.fri.rso.samples.cars.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "cars")
@NamedQueries(value =
        {
                @NamedQuery(name = "Car.getAll", query = "SELECT o FROM cars o"),
                @NamedQuery(name = "Car.findByStatus", query = "SELECT o FROM cars o WHERE o.carStatus = " +
                        ":carStatus")
        })
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;

    private String carLocation;

    private String carStatus;

    private String carModel;


    public Integer getId() {
        return carId;
    }

    public void setId(Integer id) {
        this.carId = carId;
    }

    public String getCarLocation() {
        return carLocation;
    }

    public void setCarLocation(String carLocation) {
        this.carLocation = carLocation;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) { this.carModel = carModel;
    }

}
