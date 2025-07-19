package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class DataProviders {

	@DataProvider
	public Object[][] jsonDataProvider() throws IOException {
		// Path to your JSON file
		String filePath = ".\\testdata\\product.json";

		// Read JSON file and map it to a List of Maps
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, String>> dataList = objectMapper.readValue(new File(filePath),
				new TypeReference<List<Map<String, String>>>() {
				});

		// Convert List<Map<String, String>> to Object[][]
		Object[][] dataArray = new Object[dataList.size()][];
		for (int i = 0; i < dataList.size(); i++) {
			dataArray[i] = new Object[] { dataList.get(i) };
		}

		return dataArray;
	}
	
	
	@DataProvider(name = "csvDataProvider")
    public Object[][] csvDataProvider() throws IOException {
        String filePath = ".\\testdata\\updated_products.csv";  // Update the path as needed

        List<Map<String, String>> dataList = new ArrayList<>();
        List<String> headers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1); // Preserve empty strings

                if (isFirstLine) {
                    // Read headers
                    headers.addAll(Arrays.asList(values));
                    isFirstLine = false;
                } else {
                    Map<String, String> dataMap = new HashMap<>();
                    for (int i = 0; i < headers.size(); i++) {
                        dataMap.put(headers.get(i), values[i]);
                    }
                    dataList.add(dataMap);
                }
            }
        }

        Object[][] dataArray = new Object[dataList.size()][1];
        for (int i = 0; i < dataList.size(); i++) {
            dataArray[i][0] = dataList.get(i);  // Each row is a Map
        }

        return dataArray;
    }
}

