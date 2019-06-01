<p>package com.scut.originsystem.controller;

import com.scut.originsystem.annotation.SystemLogControllerAnno;
import com.scut.originsystem.entity.*;
import com.scut.originsystem.service.GoodService;
import com.scut.originsystem.service.MerchantService;
import org.bouncycastle.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = &quot;/good&quot;)
public class GoodController {
    @Autowired
    GoodService goodService;
    @Autowired
    MerchantService merchantService;

    // upload picture
    @RequestMapping(value = &quot;/uploadPicture&quot;,method = RequestMethod.POST)
    @ResponseBody
    public Object uploadPicture(@RequestParam(value = &quot;file&quot;,required = true)MultipartFile file){
        return goodService.uploadPicture(file);
    }

    @RequestMapping(value= &quot;/getPicture&quot;,method = RequestMethod.GET)
    @ResponseBody
    public void getPictureForPromotion(@RequestParam(value=&quot;fileName&quot;,required = true)String fileName, HttpServletResponse response){
        goodService.getPicture(fileName, response);
    }


    //添加商品，自动添加商品管理表
    @RequestMapping(value = &quot;/addGood&quot;,method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = &quot;添加商品，自动添加商品管理表&quot;)
    public Object addGood(@Validated @RequestBody Good good){
        return goodService.addGood(good);
    }

    //修改商品信息
    @RequestMapping(value = &quot;/modifyGood&quot;,method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = &quot;修改商品信息&quot;)
    public Object modifyGood(@Validated @RequestBody Good good){
        return goodService.modifyGood(good);
    }

    //按页查看某商户的商品列表
    @RequestMapping(value = &quot;/getGoodList&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object getGoodList(@RequestParam(value = &quot;merchant_id&quot;, required = true) int merchant_id,
                              @RequestParam(value = &quot;page&quot;,required = false,defaultValue = &quot;1&quot;)int page){
        return goodService.getGoodList(merchant_id,page);
    }

    //产看某商户的所有商品列表
    @RequestMapping(value = &quot;/getGoodListAll&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object getGoodListAll(@RequestParam(value = &quot;merchant_id&quot;, required = true) int merchant_id,
                              @RequestParam(value = &quot;page&quot;,required = false,defaultValue = &quot;1&quot;)int page){
        return goodService.getGoodListAll(merchant_id);
    }

    //查看某商品的管理信息
    @RequestMapping(value = &quot;/getGoodManager&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object getGoodManager(@RequestParam(value = &quot;good_id&quot;, required = true) int good_id){
        return goodService.getGoodManager(good_id);
    }

    //为商品购买N个二维码
    @RequestMapping(value = &quot;/buyQRCode&quot;,method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = &quot;为商品购买N个二维码&quot;)
    public Object buyQRCode(@RequestParam(value = &quot;good_id&quot;, required = true) int good_id,
                            @RequestParam(value = &quot;number&quot;, required = true) int number){
        return goodService.buyQRCode(good_id,number);
    }

    //增加销量
    @RequestMapping(value = &quot;/sellGoods&quot;,method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = &quot;增加销量&quot;)
    public Object sellGoods(@RequestParam(value = &quot;good_id&quot;, required = true) int good_id,
                            @RequestParam(value = &quot;number&quot;, required = true) int number){
        return goodService.sellGoods(good_id,number);
    }

    //删除商品
    @RequestMapping(value = &quot;/deleteGood&quot;,method = RequestMethod.GET)
    @ResponseBody
    @SystemLogControllerAnno(description = &quot;删除商品&quot;)
    public Object deleteGood(@RequestParam(value = &quot;good_id&quot;, required = true) int good_id){
        return goodService.deleteGood(good_id);
    }

    //通过good_code、good_name、produce_place删除商品总类
    @RequestMapping(value = &quot;deleteGoodByGoodInfo&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object deleteGoodByGoodInfo(@RequestParam(value = &quot;good_code&quot;,required = true)String good_code,
                                       @RequestParam(value = &quot;good_name&quot;,required = true)String good_name,
                                       @RequestParam(value = &quot;produce_place&quot;,required = true)String produce_place,
                                       @RequestParam(value = &quot;merchant_id&quot;,required = true)int merchant_id){
        return goodService.deleteGoodByGoodInfo(good_code,good_name,produce_place,merchant_id);
    }

    //通过商品种类
    @RequestMapping(value = &quot;/findGoodsByType&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodsByType(@RequestParam(value = &quot;type_code&quot;, required = true) String type_code,
                                  @RequestParam(value = &quot;merchant_id&quot;, required = true) int merchant_id,
                                  @RequestParam(value = &quot;page&quot;,defaultValue = &quot;1&quot;)int page){
        return goodService.findGoodsByType(type_code, merchant_id,page);
    }

    //查找种类
    @RequestMapping(value = &quot;/findGoodsType&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodsType(@RequestParam(value = &quot;merchant_id&quot;, required = true) int merchant_id,
                                @RequestParam(value = &quot;page&quot;,defaultValue = &quot;1&quot;)int page){
        return goodService.findGoodsType(merchant_id,page);
    }

    //申请购买二维码
    @RequestMapping(value = &quot;/buyQRCode&quot;,method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = &quot;申请购买二维码&quot;)
    public Object buyQRCode(@Validated @RequestBody QRCodeForCheck qrCodeForCheck){
        return goodService.buyQRCode(qrCodeForCheck);
    }

    //分配二维码给指定商品
    @RequestMapping(value = &quot;/assignQRCode&quot;,method = RequestMethod.POST)
    @ResponseBody
    @SystemLogControllerAnno(description = &quot;分配二维码给商品&quot;)
    public Object assignQRCode(@Validated @RequestBody QRCodeForCheck qrCodeForCheck){
        return goodService.assginQRCode(qrCodeForCheck);
    }

    @RequestMapping(value = &quot;/findGoodInventory&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodInventory(@RequestParam(value = &quot;good_code&quot;,required = true)String good_code,
                                    @RequestParam(value = &quot;good_name&quot;,required = true)String good_name,
                                    @RequestParam(value = &quot;produce_place&quot;,required = true)String produce_place,
                                    @RequestParam(value = &quot;merchant_id&quot;,required = true)int merchant_id,
                                    @RequestParam(value = &quot;page&quot;,defaultValue = &quot;1&quot;)int page){
        return goodService.findGoodInventory(good_code,good_name,produce_place,merchant_id,page);
    }

    @RequestMapping(value = &quot;/findGoodInfoByMerchant&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodInfoByMerchant(@RequestParam(value = &quot;merchant_id&quot;)int merchant_id,
                                         @RequestParam(value = &quot;page&quot;,defaultValue = &quot;1&quot;)int page){
        return goodService.findGoodInfoByMerchant(merchant_id,page);
    }

    @RequestMapping(value = &quot;/findGoodInfoByGoodName&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodInfoByGoodName(@RequestParam(value = &quot;merchant_id&quot;)int merchant_id,
                                         @RequestParam(value = &quot;good_name&quot;)String good_name,
                                         @RequestParam(value = &quot;page&quot;,defaultValue = &quot;1&quot;)int page){
        return goodService.findGoodInfoByGoodName(merchant_id,good_name,page);
    }


    //查询某商户的可用种类表
    @RequestMapping(value = &quot;/findType&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object findTpye(@RequestParam(value = &quot;merchant_id&quot;)int merchant_id,
                           @RequestParam(value = &quot;page&quot;,defaultValue = &quot;1&quot;)int page){
        return goodService.findTpye(merchant_id,page);
    }

    //增加某商户的种类表
    @RequestMapping(value = &quot;/addType&quot;,method = RequestMethod.POST)
    @ResponseBody
    public Object addTpye(@Validated @RequestBody GoodType goodType){
        return goodService.addTpye(goodType);
    }

    //修改商品种类信息
    @RequestMapping(value = &quot;/updateGoodType&quot;,method = RequestMethod.POST)
    @ResponseBody
    public Object updateGoodType(@Validated @RequestBody GoodType goodType){
        return goodService.updateGoodType(goodType);
    }

    //删除某商户的种类表
    @RequestMapping(value = &quot;/deleteType&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object deleteTpye(@RequestParam(value = &quot;type_id&quot;)int type_id){
        return goodService.deleteType(type_id);
    }

    //通过种类id查找商品信息
    @RequestMapping(value = &quot;/findGoodByTypeId&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodByTypeId(@RequestParam(value = &quot;merchant_id&quot;)int merchant_id,
                                   @RequestParam(value = &quot;type_id&quot;)int type_id,
                                   @RequestParam(value = &quot;page&quot;,defaultValue = &quot;1&quot;)int page){
        return goodService.findGoodByTypeId(merchant_id,type_id,page);
    }

    @RequestMapping(value = &quot;/download_findGoodByTypeId&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object download_findGoodByTypeId(@RequestParam(value = &quot;merchant_id&quot;)int merchant_id,
                                            @RequestParam(value = &quot;type_id&quot;)int type_id){
        return goodService.download_findGoodByTypeId(merchant_id,type_id);
    }

    //查询某个种类表
    @RequestMapping(value = &quot;/findGoodTypeById&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodTypeById(@RequestParam(value = &quot;type_id&quot;)int type_id){
        return goodService.findGoodTypeById(type_id);
    }

    //按照条件查找商品
    @RequestMapping(value = &quot;/selectiveGood&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object selectiveGood(@RequestParam(value = &quot;merchant_id&quot;)int merchant_id,
                                @RequestParam(value = &quot;produce_place&quot;,required = false)String produce_place,
                                @RequestParam(value = &quot;pack_type&quot;,required = false)String pack_type,
                                @RequestParam(value = &quot;good_code&quot;,required = false)String good_code,
                                @RequestParam(value = &quot;good_batch&quot;,required = false)String good_batch,
                                @RequestParam(value = &quot;good_name&quot;,required = false)String good_name,
                                @RequestParam(value = &quot;type_id&quot;,required = true)int type_id,
                                @RequestParam(value = &quot;page&quot;,required = false,defaultValue = &quot;1&quot;)int page){
        return goodService.selectiveGood( produce_place, pack_type,  good_name,  good_code, merchant_id,  good_batch, type_id, page);
    }


    //按照条件查找种类
    @RequestMapping(value = &quot;/selectiveGoodType&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object selectiveGoodType(@RequestParam(value = &quot;merchant_id&quot;,required = false)int merchant_id,
                                    @RequestParam(value = &quot;good_code&quot;,required = false)String good_code,
                                    @RequestParam(value = &quot;produce_place&quot;,required = false)String produce_place,
                                    @RequestParam(value = &quot;good_name&quot;,required = false)String good_name,
                                    @RequestParam(value = &quot;page&quot;,required = false,defaultValue = &quot;1&quot;)int page){
        return goodService.selectiveGoodType(merchant_id, good_code, produce_place, good_name, page);
    }

    //查询商品
    @RequestMapping(value = &quot;/findGood_1s&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object findGood_1s(@RequestParam(value = &quot;good_code&quot;)String good_code,
                              @RequestParam(value = &quot;merchant_id&quot;)int merchant_id){
        return goodService.findGood_1s(good_code,merchant_id);
    }

    //查询商品种类
    @RequestMapping(value = &quot;/findGoodType_1s&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodType_1s(@RequestParam(value = &quot;good_code&quot;)String good_code,
                                  @RequestParam(value = &quot;merchant_id&quot;)int merchant_id){
        return goodService.findGoodType_1s(good_code,merchant_id);
    }

    //条件查询商品大类
    @RequestMapping(value = &quot;/findGoodTypesByGoodNameOrGoodCode&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object findGoodTypesByGoodNameOrGoodCode(@RequestParam(value = &quot;good_name&quot;,required = false)String good_name,
                                                @RequestParam(value = &quot;good_code&quot;,required = false)String good_code,
                                                @RequestParam(value = &quot;merchant_name&quot;,required = false)String merchant_name,
                                                @RequestParam(value = &quot;page&quot;,required = false,defaultValue = &quot;1&quot;)int page,
                                                HttpServletRequest request){
        return goodService.findGoodTypesByGoodNameOrGoodCode(good_name,good_code,merchant_name,page,request);
    }

    @RequestMapping(value = &quot;/download_findGoodTypesByGoodNameOrGoodCode&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object download_findGoodTypesByGoodNameOrGoodCode(@RequestParam(value = &quot;good_name&quot;,required = false)String good_name,
                                                             @RequestParam(value = &quot;good_code&quot;,required = false)String good_code,
                                                             @RequestParam(value = &quot;merchant_name&quot;,required = false)String merchant_name,
                                                             HttpServletRequest request){
        return goodService.download_findGoodTypesByGoodNameOrGoodCode(good_name,good_code,merchant_name,request);
    }

    //获取全部商品大类
    @RequestMapping(value = &quot;/findAllGoodTypes&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object findAllGoodTypes(@RequestParam(value = &quot;page&quot;,required = false,defaultValue = &quot;1&quot;)int page,
                                   HttpServletRequest request){
        return goodService.findAllGoodTypes(page,request);
    }

    @RequestMapping(value = &quot;/addVideoDevice2GoodType&quot;,method = RequestMethod.POST)
    @ResponseBody
    public Object addVideoDevice2GoodType(@RequestBody VideoDevice2GoodType videoDevice2GoodType){
        return goodService.addVideoDevice2GoodType(videoDevice2GoodType);
    }

    @RequestMapping(value = &quot;/deleteVideoDevice2GoodType&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object deleteVideoDevice2GoodType(@RequestParam(value=&quot;id&quot;)int id){
        return goodService.deleteVideoDevice2GoodType(id);
    }

    @RequestMapping(value = &quot;/deleteVideoDevice2GoodTypeByVideoDeviceIDAndGoodTypeID&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object deleteVideoDevice2GoodTypeByVideoDeviceIDAndGoodTypeID(@RequestParam(value=&quot;video_device_id&quot;)int video_device_id,
                                                                         @RequestParam(value = &quot;good_type_id&quot;)int good_type_id){
        return goodService.deleteVideoDevice2GoodTypeByVideoDeviceIDAndGoodTypeID(video_device_id,good_type_id);
    }

    @RequestMapping(value = &quot;/getGoodTypesByVideoDevice&quot;,method = RequestMethod.GET)
    @ResponseBody
    public Object getGoodTypesByVideoDevice(@RequestParam(value = &quot;video_device_id&quot;)int video_device_id,
                                            @RequestParam(value=&quot;page&quot;,required = false,defaultValue = &quot;0&quot;)int page){
        return goodService.getGoodTypesByVideoDevice(video_device_id,page);
    }

    @RequestMapping(value = &quot;/addGoodComment&quot;, method = RequestMethod.POST)
    @ResponseBody
    public Object addGoodComment(@RequestBody GoodComment goodComment){
        return goodService.addGoodComment(goodComment);
    }

    @RequestMapping(value = &quot;/getGoodCommentByGoodID&quot;, method = RequestMethod.GET)
    @ResponseBody
    public Object getGoodCommentByGoodID(@RequestParam(value = &quot;good_id&quot;)int good_id,
                                             @RequestParam(value = &quot;page&quot;,required = false,defaultValue = &quot;0&quot;)int page){
        return goodService.getGoodCommentByGoodID(good_id,page);
    }


}
</p>