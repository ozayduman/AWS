package com.ozayduman.socialapp.firends.user.messaging;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class SendMessageFunctionHandler implements RequestHandler<SendMessageRequest, SendMessageResponse> {

    @Override
    public SendMessageResponse handleRequest(SendMessageRequest request, Context context) {
        context.getLogger().log("Input: " + request);
        Message message = request.convertToMessage();
        
        return new SendMessageResponse();
    }

}
