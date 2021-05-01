package de.jkueck.fire.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseController {

    public Sort getSortParameter(String sort) {

        // sort=field1,field2,field3
        // sort=field1:asc,field2,field3
        // sort=field1:asc,field2:desc,field3:asc

        if (StringUtils.isNotBlank(sort)) {

            List<Sort.Order> orderList = new ArrayList<>();
            String[] fields = sort.split(",");
            for (String field : fields) {
                String[] fieldData = field.split(":");
                if (Arrays.stream(fieldData).count() == 1) {
                    orderList.add(Sort.Order.by(field));
                } else {
                    if (fieldData[1].equals("asc")) {
                        orderList.add(Sort.Order.asc(fieldData[0]));
                    } else {
                        orderList.add(Sort.Order.desc(fieldData[0]));
                    }
                }
            }
            return Sort.by(orderList);
        }
        return Sort.unsorted();
    }

}
