package payloads;

import java.util.Random;

import pojo.Address;
import com.github.javafaker.Faker;

import pojo.Geolocation;
import pojo.Name;
import pojo.Product;
import pojo.User;


public class Payload {
	
	private static final Faker faker=new Faker();
	private static final String categories[]= {"electronics","funiture","clothing","books","beauty"};
	
	
	private static final Random random =new Random();
	
	
	//Product
	public static Product productPayload()
	{
		String name=faker.commerce().productName();
		double price=Double.parseDouble(faker.commerce().price());
		String description=faker.lorem().sentence();
		String imageUrl="https://i.pravatar.cc/100";
		String category=categories[random.nextInt(categories.length)];
		//int id = random.nextInt(1000); // You can generate a random id or use any logic
		
		return new Product (name,price, description, imageUrl,category);
		
		
	}
	
	
	
	
	
	//Cart
	
	
	
	
	//User
	
	public static User userPayload()
	{
		
		//Name
		String firstname=faker.name().firstName();
		String lastname=faker.name().lastName();
		Name name=new Name(firstname,lastname);
		
		
		//Location
		 String lat=faker.address().latitude();
		 String lng=faker.address().longitude();
		 
		 Geolocation location =new Geolocation(lat,lng);
		
		
		
		//Address
		String city=faker.address().city();
		String street=faker.address().streetName();
		int number=random.nextInt(100);
		String zipcode=faker.address().zipCode();
		
		Address address=new Address(city,street,number,zipcode,location);
		
		//User
		String email=faker.internet().emailAddress();
		String username=faker.name().username();
		String pwd=faker.internet().password();
		String phonenumber=faker.phoneNumber().cellPhone();
		int id = random.nextInt(1000); // You can generate a random id or use any logic
		User user=new User(id,username,email,pwd,name,address,phonenumber);

	   return user;
	}
	
	
	
	
	//Login 
}

