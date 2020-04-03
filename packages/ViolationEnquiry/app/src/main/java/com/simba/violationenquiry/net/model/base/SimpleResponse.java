/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.simba.violationenquiry.net.model.base;

import java.io.Serializable;

/**
 * @Author : chenjianbo
 * @Date : 2020/2/27
 * @Desc :
 */
public class SimpleResponse implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;


    /**
     * 页码
     */
    public int pageIndex;
    /**
     * 分页的大小
     */
    public int pageSize;
    /**
     * 总条数
     */
    public long total;

    public GeneralResponse toGeneralResponse() {
        GeneralResponse lzyResponse = new GeneralResponse();

        return lzyResponse;
    }
}
