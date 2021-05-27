package com.melson.webserver.order.service.impl;

import com.melson.webserver.dict.service.IBoms;
import com.melson.webserver.dict.vo.BomProcessVo;
import com.melson.webserver.dict.vo.BomVo;
import com.melson.webserver.order.dao.IProducePlanProcessRepository;
import com.melson.webserver.order.entity.ProducePlan;
import com.melson.webserver.order.entity.ProducePlanDetail;
import com.melson.webserver.order.entity.ProducePlanProcess;
import com.melson.webserver.order.service.IProducePlanProcessService;
import com.melson.webserver.produce.dao.IProducePlanWorkStationRepository;
import com.melson.webserver.produce.entity.ProducePlanWorkStation;
import com.melson.webserver.produce.vo.ProducePlanMaterialVo;
import com.melson.webserver.produce.vo.ProducePlanProcessVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/12
 */
@Service
public class ProducePlanProcessServiceImpl implements IProducePlanProcessService {
    @Autowired
    private IBoms bomsService;
    @Autowired
    private IProducePlanProcessRepository producePlanProcessRepository;
    @Autowired
    private IProducePlanWorkStationRepository producePlanWorkStationRepository;

    @Override
    public List<ProducePlanProcess> GeneratePlanProcess(ProducePlan plan, List<ProducePlanDetail> detailList) {
        if (detailList == null || detailList.size() <= 0) return null;
        Set<String> bomNos = new HashSet<>();
        detailList.forEach(detail -> {
            String[] bomIdArr = detail.getBomNos().split("-");
            for (String no : bomIdArr) {
                if (!StringUtils.isEmpty(no)) {
                    bomNos.add(no);
                }
            }
        });
        List<BomProcessVo> bomProcessVoList = bomsService.findBomProcessVoInBomNos(bomNos);
        //按照bomNo 分类 防止detail 出现相同product 的bug 以detail 生成ProducePlanProcess
        Map<String, List<BomProcessVo>> bomProcessVoMap = new HashMap<>();
        for (BomProcessVo vo : bomProcessVoList) {
            List<BomProcessVo> existList = bomProcessVoMap.get(vo.getBomNo());
            if (existList == null) {
                existList = new ArrayList<>();
                existList.add(vo);
                bomProcessVoMap.put(vo.getBomNo(), existList);
            } else {
                existList.add(vo);
            }
        }

        List<ProducePlanProcess> planProcessList = new ArrayList<>();
        //生成 ProducePlanProcess
        for (ProducePlanDetail producePlanDetail : detailList) {
            List<BomProcessVo> boms = new ArrayList<>();
            String[] detialBomNos = producePlanDetail.getBomNos().split("-");
            if (detialBomNos.length <= 0) continue;
            for (String bomNo : detialBomNos) {
                boms.addAll(bomProcessVoMap.get(bomNo));
            }
            List<BomProcessVo> sortedList = GenerateBomTree(boms, producePlanDetail.getBomNo());
//            List<BomProcessVo> rootList = FindRootList(sortedList);
            for (BomProcessVo vo : sortedList) {
                ProducePlanProcess p = CreatePlanProcess(vo, plan, producePlanDetail);
                planProcessList.add(p);
            }
        }
        return producePlanProcessRepository.saveAll(planProcessList);
    }

    @Override
    public List<ProducePlanProcess> FindByPlanId(Integer planId) {
        return producePlanProcessRepository.findByPlanId(planId);
    }

    @Override
    public List<ProducePlanProcessVo> FindPlanProcess(Integer planId, Integer planDetailId) {
        List<ProducePlanProcess> processList = producePlanProcessRepository.findByPlanIdAndPlanDetailIdOrderByProcessIndex(planId, planDetailId);
        List<ProducePlanProcessVo> voList = GroupToVoList(processList);
        FillWorkStation(voList, planId);
        return voList;
    }


    /**
     * 获取底层物料清单
     *
     * @param bomProcessVoList
     * @return
     */
    private List<BomProcessVo> FindRootList(List<BomProcessVo> bomProcessVoList) {
        Set<String> partNoSet = new HashSet<>();
        bomProcessVoList.forEach(bomVo -> partNoSet.add(bomVo.getPartNo()));
        List<BomProcessVo> rootList = new ArrayList<>();
        for (BomProcessVo vo : bomProcessVoList) {
            String key = vo.getChPartNo();
            if (partNoSet.contains(key)) continue;
            rootList.add(vo);
        }
        return rootList;
    }


    private List<BomProcessVo> GenerateBomTree(List<BomProcessVo> bomVos, String rootBomNo) {
        List<BomProcessVo> res = new ArrayList<>();
        List<BomProcessVo> remindList = new ArrayList<>();
        for (BomProcessVo bomVo : bomVos) {
            if (bomVo.getBomNo().equals(rootBomNo)) {
                res.add(bomVo);
            } else {
                remindList.add(bomVo);
            }
        }
        for (BomProcessVo vo : res) {
            List<BomProcessVo> childList = FindChildList(vo.getChPartNo(), remindList);
            childList.forEach(bomVo -> {
                bomVo.setIndex(vo.getIndex() + "-" + bomVo.getIndex());
            });
            vo.setChildList(childList);
        }
        return SetProcessVoIndex(res);
    }

    private List<BomProcessVo> SetProcessVoIndex(List<BomProcessVo> res) {
        List<BomProcessVo> vos = new ArrayList<>();
        for (BomProcessVo vo : res) {
            vos.add(vo);
            List<BomProcessVo> list = GetAllChildList(vo);
            if (list != null) vos.addAll(list);
        }
        return vos;
    }

    private List<BomProcessVo> GetAllChildList(BomProcessVo vo) {
        List<BomProcessVo> vos = new ArrayList<>();
        if (vo.getChildList() == null || vo.getChildList().size() <= 0) return null;
        for (BomProcessVo child : vo.getChildList()) {
            List<BomProcessVo> list = GetAllChildList(child);
            vos.add(child);
            if (list != null) vos.addAll(list);
        }
        return vos;
    }


    private List<BomProcessVo> FindChildList(String childNo, List<BomProcessVo> remindList) {
        List<BomProcessVo> childList = new ArrayList<>();
        List<BomProcessVo> reList = new ArrayList<>();
        for (BomProcessVo vo : remindList) {
            if (vo.getPartNo().equals(childNo)) {
                childList.add(vo);
            } else {
                reList.add(vo);
            }
        }
        if (reList.size() > 0) {
            for (BomProcessVo vo : childList) {
                List<BomProcessVo> childList2 = FindChildList(vo.getChPartNo(), remindList);
                childList2.forEach(bomVo -> {
                    bomVo.setIndex(vo.getIndex() + "-" + bomVo.getIndex());
                });
                vo.setChildList(childList2);
            }
        }
        return childList;
    }

    private ProducePlanProcess CreatePlanProcess(BomProcessVo vo, ProducePlan plan, ProducePlanDetail producePlanDetail) {
        ProducePlanProcess process = new ProducePlanProcess();
        process.setPlanId(plan.getId());
        process.setProductId(producePlanDetail.getProductId());
        process.setProcessId(vo.getProcessId());
        process.setProcessNo(vo.getProcessNo());
        process.setProcessName(vo.getProcessName());
        process.setMaterialNo(vo.getChPartNo());
        process.setPartMaterialNo(vo.getPartNo());
        process.setMaterialName(vo.getMaterialName());
        process.setMaterialCount(vo.getMaterialCount());
        process.setBomNo(vo.getBomNo());
        process.setProcessIndex(vo.getIndex());
        process.setDelegateFlag(vo.getDelegateFlag());
        process.setPlanDetailId(producePlanDetail.getId());
        return process;
    }

    private List<ProducePlanProcessVo> GroupToVoList(List<ProducePlanProcess> processList) {
        Map<String, List<ProducePlanProcess>> producePlanProcessGroup = new HashMap<>();
        //以 planId+processId 为key 分组
        Integer indexLen = 0;
        for (ProducePlanProcess producePlanProcess : processList) {
            Integer pIndexLen = producePlanProcess.getProcessIndex().split("-").length;
            if (pIndexLen > indexLen) {
                indexLen = pIndexLen;
            }
            String key = producePlanProcess.getPlanId() + producePlanProcess.getProcessId().toString();
            List<ProducePlanProcess> existList = producePlanProcessGroup.get(key);
            if (existList == null) {
                existList = new ArrayList<>();
                existList.add(producePlanProcess);
                producePlanProcessGroup.put(key, existList);
            } else {
                existList.add(producePlanProcess);
            }
        }
        List<ProducePlanProcessVo> voList = new ArrayList<>();
        for (List<ProducePlanProcess> pList : producePlanProcessGroup.values()) {
            voList.add(CreateProducePlanProcessVo(pList, indexLen));
        }
        voList.sort(new Comparator<ProducePlanProcessVo>() {
            @Override
            public int compare(ProducePlanProcessVo o1, ProducePlanProcessVo o2) {
                Integer index1 = Integer.valueOf(o1.getIndex());
                Integer index2 = Integer.valueOf(o2.getIndex());
                return index1 < index2 ? -1 : 0;
            }
        });
        return voList;
    }

    private ProducePlanProcessVo CreateProducePlanProcessVo(List<ProducePlanProcess> processList, Integer indexLength) {
        ProducePlanProcessVo vo = new ProducePlanProcessVo();
        ProducePlanProcess example = processList.get(0);
        vo.setPlanId(example.getPlanId());
        vo.setProcessId(example.getProcessId());
        vo.setProcessNo(example.getProcessNo());
        vo.setProcessName(example.getProcessName());
        vo.setBomNo(example.getBomNo());
        vo.setDelegateFlag(example.getDelegateFlag());
        String replaceIndex = processList.get(processList.size() - 1).getProcessIndex().replace("-", "");
        if (replaceIndex.length() < indexLength) {
            Integer baseZero = new Double(Math.pow(10.0, Double.valueOf(indexLength - replaceIndex.length()))).intValue();
            String zeroString = baseZero.toString().substring(1);
            replaceIndex += zeroString;
        }
        vo.setIndex(replaceIndex);
        List<ProducePlanMaterialVo> materialVos = new ArrayList<>();
        for (ProducePlanProcess p : processList) {
            materialVos.add(new ProducePlanMaterialVo(p.getMaterialNo(), p.getMaterialName(), p.getMaterialCount()));
        }
        vo.setMaterialVos(materialVos);
        return vo;
    }

    private void FillWorkStation(List<ProducePlanProcessVo> voList, Integer planId) {
        List<ProducePlanWorkStation> workStationList = producePlanWorkStationRepository.findByPlanId(planId);
        //生产计划按processId 分组
        Map<Integer, List<ProducePlanWorkStation>> workStationPlanMap = new HashMap<>();
        for (ProducePlanWorkStation workStation : workStationList) {
            List<ProducePlanWorkStation> existList = workStationPlanMap.get(workStation.getProcessId());
            if (existList == null) {
                existList = new ArrayList<>();
                existList.add(workStation);
                workStationPlanMap.put(workStation.getProcessId(), existList);
            } else {
                existList.add(workStation);
            }
        }

        //填充工序列表
        for (ProducePlanProcessVo vo : voList) {
            List<ProducePlanWorkStation> voStationList = workStationPlanMap.get(vo.getProcessId());
            if (voStationList != null) {
                vo.setWorkStationList(voStationList);
            } else {
                vo.setWorkStationList(new ArrayList<>());
            }
        }
    }
}
