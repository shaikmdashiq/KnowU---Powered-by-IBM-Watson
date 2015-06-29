/**
 * Copyright 2015 IBM Corp. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.artqueen.knowu;

import com.ibm.watson.developer_cloud.personality_insights.v2.model.Content;
import com.ibm.watson.developer_cloud.personality_insights.v2.model.Profile;
import com.ibm.watson.developer_cloud.service.Request;
import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.util.MediaType;
import com.ibm.watson.developer_cloud.util.ResponseUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HTTP;

import java.io.IOException;

public class PersonalityInsights extends WatsonService {

    public PersonalityInsights() {
        String URL = "https://gateway.watsonplatform.net/personality-insights/api";
        setEndPoint(URL);
    }

    public Profile getProfile(Content content) {
        if (content == null)
            throw new IllegalArgumentException("content can not be null");

        if (content.getContentItems() == null || content.getContentItems().isEmpty())
            throw new IllegalArgumentException("content needs to have contentItems.");

        String contentJson = getGson().toJson(content);
        HttpRequestBase request = Request.Post("/v2/profile")
                .withContent(contentJson, MediaType.APPLICATION_JSON).build();

        try {
            HttpResponse response = execute(request);
            String profileJson = ResponseUtil.getString(response);
            return getGson().fromJson(profileJson, Profile.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProfile(String text) {
        if (text == null)
            throw new IllegalArgumentException("text can not be null");

        HttpRequestBase request = Request.Post("/v2/profile")
                .withContent(text, HTTP.PLAIN_TEXT_TYPE).build();

        HttpResponse response = execute(request);
        try {
            return ResponseUtil.getString(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "PersonalityInsights [getEndPoint()=" + getEndPoint() + "]";
    }

}
