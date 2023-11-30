package org.dromara.workflow.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.resource.ResourceUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.common.web.core.BaseController;
import org.dromara.workflow.domain.bo.ModelBo;
import org.dromara.workflow.domain.vo.AccountVo;
import org.dromara.workflow.domain.vo.ResultListDataRepresentation;
import org.dromara.workflow.service.IActModelService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 模型管理 控制层
 *
 * @author may
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/model")
public class ActModelController extends BaseController {

    private final RepositoryService repositoryService;

    private final IActModelService actModelService;


    /**
     * 分页查询模型
     *
     * @param modelBo 模型参数
     */
    @GetMapping("/list")
    public TableDataInfo<Model> page(ModelBo modelBo) {
        return actModelService.page(modelBo);
    }

    /**
     * 设计器登录信息
     */
    @GetMapping("/rest/account")
    public String getAccount() {
        AccountVo accountVo = new AccountVo();
        LoginUser loginUser = LoginHelper.getLoginUser();
        accountVo.setId(Convert.toStr(loginUser.getUserId()));
        accountVo.setFirstName("");
        accountVo.setLastName(loginUser.getUsername());
        accountVo.setFullName(loginUser.getUsername());
        return JsonUtils.toJsonString(accountVo);
    }

    /**
     * 新增模型
     *
     * @param modelBo 模型请求对象
     */
    @Log(title = "模型管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/rest/models")
    public R<Void> saveNewModel(@Validated(AddGroup.class) @RequestBody ModelBo modelBo) {
        return toAjax(actModelService.saveNewModel(modelBo));
    }

    /**
     * 查询模型
     *
     * @param modelId 模型id
     */
    @GetMapping("/rest/models/{modelId}/editor/json")
    public ObjectNode getModelInfo(@NotBlank(message = "模型id不能为空") @PathVariable String modelId) {
        return actModelService.getModelInfo(modelId);
    }


    /**
     * 编辑模型
     *
     * @param modelId 模型id
     * @param values  模型数据
     */
    @Log(title = "模型管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping(value = "/rest/models/{modelId}/editor/json")
    public R<Void> editModel(@PathVariable String modelId, @RequestParam MultiValueMap<String, String> values) {
        return toAjax(actModelService.editModel(modelId, values));
    }

    /**
     * 删除流程模型
     *
     * @param ids 模型id
     */
    @Log(title = "模型管理", businessType = BusinessType.DELETE)
    @RepeatSubmit()
    @DeleteMapping("/{ids}")
    @Transactional(rollbackFor = Exception.class)
    public R<Void> delete(@NotEmpty(message = "主键不能为空") @PathVariable String[] ids) {
        Arrays.stream(ids).parallel().forEachOrdered(repositoryService::deleteModel);
        return R.ok();
    }

    /**
     * 模型部署
     *
     * @param id 模型id
     */
    @Log(title = "模型管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/modelDeploy/{id}")
    public R<Void> deploy(@NotBlank(message = "模型id不能为空") @PathVariable("id") String id) {
        return toAjax(actModelService.modelDeploy(id));
    }

    /**
     * 导出模型zip压缩包
     *
     * @param modelId  模型id
     * @param response 相应
     */
    @GetMapping("/export/zip/{modelId}")
    public void exportZip(@NotEmpty(message = "模型id不能为空") @PathVariable String modelId,
                          HttpServletResponse response) {
        actModelService.exportZip(modelId, response);
    }

    /**
     * 查询用户
     *
     * @param filter 参数
     */
    @GetMapping(value = "/rest/editor-users")
    public ResultListDataRepresentation editorUsers(@RequestParam(value = "filter", required = false) String filter) {
        return actModelService.getUsers(filter);
    }

    /**
     * 查询用户组
     *
     * @param filter 参数
     */
    @GetMapping(value = "/rest/editor-groups")
    public ResultListDataRepresentation editorGroups(@RequestParam(required = false, value = "filter") String filter) {
        return actModelService.getGroups(filter);
    }

    /**
     * 解析翻译模型设计器内容
     */
    @GetMapping(value = "/rest/stencil-sets/editor")
    public String getStencilset() {
        try (InputStream inputStream = ResourceUtil.getStream("static/stencilset.json")) {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new SecurityException("Error while loading stencil set", e);
        }
    }
}
