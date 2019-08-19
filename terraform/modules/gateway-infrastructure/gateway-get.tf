#API METHODS
resource "aws_api_gateway_method" "get_campaigns_method" {
  rest_api_id = "${aws_api_gateway_rest_api.gateway_rest_api.id}"
  resource_id = "${aws_api_gateway_resource.items_resource.id}"
  http_method = "GET"
  authorization = "NONE"
}

#API INTEGRATION
resource "aws_api_gateway_integration" "get_campaigns_integration" {
  rest_api_id = "${aws_api_gateway_rest_api.gateway_rest_api.id}"
  resource_id = "${aws_api_gateway_resource.items_resource.id}"
  http_method = "${aws_api_gateway_method.get_campaigns_method.http_method}"
  integration_http_method = "POST"
  type = "AWS_PROXY"
  uri = "arn:${data.aws_partition.current.partition}:apigateway:${data.aws_region.current.name}:lambda:path/2015-03-31/functions/${data.aws_lambda_function.get_items_lambda.arn}/invocations"
}