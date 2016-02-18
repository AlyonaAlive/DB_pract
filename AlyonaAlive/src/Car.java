public class Car {

	public Car(String nameOfCar, String idOfCar, String Company) {
		super();
		this.name = nameOfCar;
		this.passport = idOfCar;
		this.address = Company;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return name;
	}
	
	private String name;
	private String passport;
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getAddress() {
		return address;
	}

}
