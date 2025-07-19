package payloads;

import java.util.Random;

import com.github.javafaker.Faker;
import pojo.Product;

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
	
	
	
	
	//Login 

}
