console.log('Loading function');

var AWS = require('aws-sdk');
var lambda = new AWS.Lambda();
var pipeline = new AWS.CodePipeline();


exports.handler = function(event, context) {
    console.log('Received event:', JSON.stringify(event, null, 2));

    var key = event["CodePipeline.job"].data.inputArtifacts[0].location.s3Location.objectKey;
    var bucket = event["CodePipeline.job"].data.inputArtifacts[0].location.s3Location.bucketName;
    var jobId = event["CodePipeline.job"].id;
    var functionName = event["CodePipeline.job"].data.actionConfiguration.configuration.UserParameters;

    console.log("received s3 object " + bucket + " " + key);

    var params = {
        FunctionName: functionName,
        S3Key: key,
        S3Bucket: bucket
    };

    console.log("uploading to lambda function: " + functionName);

    lambda.updateFunctionCode(params, function(err, data) {
        if (err) {
            var pipeParams = {
                jobId: jobId,
                failureDetails: {
                    message: JSON.stringify("Failed updateFunctionCode for Lambda: " + functionName),
                    type: 'JobFailed',
                    externalExecutionId: context.invokeid
                }
            };
            console.log("error");
            console.log(pipeParams);
            console.log(err);
            pipeline.putJobFailureResult(pipeParams, function(err, data) {
                console.log(err);
                context.fail(err);
            });
        } else {
            console.log("ok");
            var pipeParams = { jobId: jobId };
            console.log(pipeParams);
            pipeline.putJobSuccessResult(pipeParams, function(err, data) {
                if(err) {
                    context.fail(err);
                } else {
                    context.succeed(data);
                }
            });
        }
    });
};