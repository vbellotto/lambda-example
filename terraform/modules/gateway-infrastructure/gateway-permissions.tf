#API PERMISSION
resource "aws_lambda_permission" "get_campaign_permission" {
  statement_id = "AllowGetItemExecutionFromAPIGateway"
  action = "lambda:InvokeFunction"
  function_name = "${data.aws_lambda_function.get_items_lambda.function_name}"
  principal = "apigateway.amazonaws.com"
  source_arn = "${aws_api_gateway_rest_api.gateway_rest_api.execution_arn}/*/*/*"
}