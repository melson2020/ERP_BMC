package com.melson.webserver.dict.service.impl;

import com.melson.base.utils.EntityUtils;
import com.melson.webserver.dict.dao.IProduceLineRepository;
import com.melson.webserver.dict.dao.IProduceWorkStationRepository;
import com.melson.webserver.dict.entity.ProduceLine;
import com.melson.webserver.dict.entity.ProduceWorkStation;
import com.melson.webserver.dict.service.IProduceLineService;
import com.melson.webserver.order.dao.IProducePlanRepository;
import com.melson.webserver.produce.vo.ProcessingWorkStationVo;
import com.melson.webserver.produce.vo.ProduceLineStateVo;
import com.melson.webserver.produce.vo.ProduceWorkStationStateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/10
 */
@Service
public class ProduceLineServiceImpl implements IProduceLineService {
    @Autowired
    private IProduceLineRepository produceLineRepository;
    @Autowired
    private IProduceWorkStationRepository workStationRepository;
    @Autowired
    private IProducePlanRepository producePlanRepository;

    @Override
    @Transactional
    public ProduceLine SaveOne(ProduceLine line) {
        ProduceLine saved = produceLineRepository.save(line);
        List<ProduceWorkStation> workStations = line.getWorkStationList();
        workStations.forEach(produceWorkStation -> {
            produceWorkStation.setProduceLineId(saved.getId());
            produceWorkStation.setProduceLineName(saved.getName());
        });
        workStationRepository.saveAll(workStations);
        return saved;
    }

    @Override
    public List<ProduceLine> FindAll() {
        return produceLineRepository.findAll();
    }

    @Override
    @Transactional
    public void DeleteOne(Integer id) {
        produceLineRepository.deleteById(id);
        workStationRepository.deleteByProduceLineId(id);
    }

    @Override
    public List<ProduceLineStateVo> GetProduceLineStateInfo() {
        List<Object[]> objects = produceLineRepository.findProduceLineAndWorkStation();
        List<Object[]> usingList=producePlanRepository.findProcessingWorkStation();
        List<ProcessingWorkStationVo>  processingWorkStationVos= EntityUtils.castEntity(usingList,ProcessingWorkStationVo.class,new ProcessingWorkStationVo());
        Map<Integer,List<ProcessingWorkStationVo>> processingWorkStationMap=new HashMap<>();
        Map<Integer, List<Object[]>> lineIdMap = new HashMap<>();
        for (Object[] obj : objects) {
            Integer lineId = Integer.parseInt(obj[0].toString());
            List<Object[]> existList = lineIdMap.get(lineId);
            if (existList == null) {
                existList = new ArrayList<>();
                existList.add(obj);
                lineIdMap.put(lineId, existList);
            } else {
                existList.add(obj);
            }
        }
        for (ProcessingWorkStationVo vo : processingWorkStationVos) {
           List<ProcessingWorkStationVo> existList=processingWorkStationMap.get(vo.getWorkStationId());
            if (existList == null) {
                existList = new ArrayList<>();
                existList.add(vo);
                processingWorkStationMap.put(vo.getWorkStationId(), existList);
            } else {
                existList.add(vo);
            }
        }
        List<ProduceLineStateVo> lines=new ArrayList<>();
        for(List<Object[]> list:lineIdMap.values()){
            lines.add(GenerateLine(list,processingWorkStationMap));
        }
        return lines;
    }

    private ProduceLineStateVo GenerateLine(List<Object[]> objects,Map<Integer,List<ProcessingWorkStationVo>> processingWorkStationMap) {
        List<ProduceWorkStationStateVo> produceWorkStationStateVos = new ArrayList<>(objects.size());
        for (Object[] obj : objects) {
            produceWorkStationStateVos.add(GenerateWorkStationVo(obj,processingWorkStationMap));
        }
        if (produceWorkStationStateVos.size() <= 0) return null;
        Object[] example = objects.get(0);
        ProduceLineStateVo vo = new ProduceLineStateVo();
        vo.setLineId(Integer.parseInt(example[0].toString()));
        vo.setLineName(example[1].toString());
        vo.setWorkStationVoList(produceWorkStationStateVos);
        return vo;
    }

    private ProduceWorkStationStateVo GenerateWorkStationVo(Object[] obj,Map<Integer,List<ProcessingWorkStationVo>> processingWorkStationMap) {
        ProduceWorkStationStateVo vo = new ProduceWorkStationStateVo();
        vo.setLineId(Integer.parseInt(obj[0].toString()));
        vo.setIndexNo(obj[2].toString());
        vo.setWorkStationName(obj[3].toString());
        vo.setWorkStationId(Integer.parseInt(obj[4].toString()));
        vo.setEmployeeGroupNo(obj[5].toString());
        vo.setPlanInfo(processingWorkStationMap.get(vo.getWorkStationId())==null?new ArrayList<>():processingWorkStationMap.get(vo.getWorkStationId()));
        return vo;
    }
}
