package com.opencode.bug.sort;

import lombok.Data;

import java.util.*;

/**
 * Comparison method violates its general contract!
 * @date 2022/4/21 4:14 下午
 */
public class FindSortError {

    @Data
    static class UserMatchCarResultVO {
        private String carId;
        private String orgId;
        private String shopCode;
        private Boolean isCompetition;
        private Double score;
        private transient String score_desc;
    }

    public static void main(String[] args) {

        List<UserMatchCarResultVO> matchedCarList = new ArrayList<>();
        Map<String, Date> instockMap = new HashMap<>(matchedCarList.size());

        for(int i = 0 ; i < 100 ; i++){
            UserMatchCarResultVO result = new UserMatchCarResultVO();
            String carId = i + "_car_id";
            result.setCarId(carId);

            if(i % 2 == 0){
                result.setIsCompetition(true);
            }
            result.setOrgId(i + "_org_id");
            result.setShopCode(i + "_shop_code");

            if(i < 3){
                result.setScore(Double.valueOf(i+""));
            }else {
                if(i % 2 == 0){
                    result.setScore(null);
                }else{
                    result.setScore(Double.valueOf(i + "" ));
                }

            }

            if(i < 15 ){
                instockMap.put(carId , null);
            }else{
                instockMap.put(carId , new Date());
            }

            matchedCarList.add(result);
        }

        matchedCarList.sort(new Comparator<UserMatchCarResultVO>() {
            @Override
            public int compare(UserMatchCarResultVO o1, UserMatchCarResultVO o2) {
                if (o1.getScore() != null && o2.getScore() != null
                        && !o1.getScore().equals(o2.getScore())) {
                    // 比较匹配度
                    return o2.getScore().compareTo(o1.getScore());
                } else {
                    // 比较日期
                    Date d1 = instockMap.getOrDefault(o1.getCarId(), null);
                    Date d2 = instockMap.getOrDefault(o2.getCarId(), null);
                    if (d1 != null && d2 != null) {
                        return d2.compareTo(d1);
                    }

                }
            return 0;
            }
        });

        System.out.println(matchedCarList);

//        sort(1, 2, 3, 2, 2, 3, 2, 3, 2, 2, 3, 2, 3, 3, 2, 2, 2, 2, 2, 2, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 , 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
//
//        System.out.println(111);

    }

    private static void sort(Integer... ints) {
        List<Integer> list = Arrays.asList(ints);
        list.sort((o1, o2) -> {
            if (o1 < o2) {
                return -1;
            } else {
                return 1;
            }
        });
        System.out.println(list);
    }

}
