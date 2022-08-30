package com.opencode.car.base.area;

import com.alibaba.fastjson.JSONArray;
import com.opencode.car.entity.AreaDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;

public class AreaService {

    private static final List<AreaDO> provinceList = new ArrayList<>();
    private static final Map<String,List<AreaDO>> cityList = new LinkedHashMap<>();
    private static final Map<String, AreaDO> provinceMap = new LinkedHashMap<>();
    private static final Map<String, AreaDO> cityMap = new LinkedHashMap<>();

    static {

        final String areaJsonPath = System.getProperty("user.dir") + File.separator + "src/main/java/com/opencode/car/area/area.json";
        File file = new File(areaJsonPath);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            String data;
            while((data = br.readLine()) != null){
                List<AreaDO> areaDOS = JSONArray.parseArray(data, AreaDO.class);
                for(AreaDO areaDO : areaDOS){

                    String provinceCode = areaDO.getProvinceCode();
                    if(!provinceMap.containsKey(provinceCode)){
                        provinceMap.put(provinceCode , areaDO);

                        AreaDO area = new AreaDO();
                        area.setProvinceCode(areaDO.getProvinceCode());
                        area.setName(areaDO.getName());
                        area.setProvinceName(areaDO.getProvinceName());
                        provinceList.add(area);

                    }

                    String cityCode = areaDO.getCityCode();
                    if(!cityMap.containsKey(cityCode)){
                        cityMap.put(cityCode , areaDO);
                        List<AreaDO> cities = cityList.get(provinceCode);
                        if(CollectionUtils.isEmpty(cities)){
                            cityList.put(provinceCode , new ArrayList<AreaDO>(){{
                                add(areaDO);
                            }});
                        }else{
                            cities.add(areaDO);
                        }

                    }

                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public List<AreaDO> getAllProvince(){
        return provinceList;
    }

    public List<AreaDO> getCityByProvinceCode(String code){
        if (StringUtils.isEmpty(code)){
            return null;
        }
        return cityList.get(code);
    }

    public AreaDO getProvinceByCode(String code){
        if (StringUtils.isEmpty(code)){
            return null;
        }
        return provinceMap.get(code);
    }

    public AreaDO getCityByCode(String code){
        if (StringUtils.isEmpty(code)){
            return null;
        }
        return cityMap.get(code);
    }

}
