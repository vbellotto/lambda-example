locals {
  // At this time Terraform is incapable of automating the API Gateway deployment by default.
  // To achieve this we search for changes made to the API Gateway infrastructure files and trigger the deployment.
  // In order to automate deployment of changes made to new files please add them below.
  file_hashes = [
    "${md5(file("${path.module}/gateway-core.tf"))}",
    "${md5(file("${path.module}/gateway-get.tf"))}"
  ]
  combined_hash = "${join(",", local.file_hashes)}"
}

resource "aws_api_gateway_rest_api" "gateway_rest_api" {
  name = "${var.project_name}-${data.aws_region.current.name}-${var.stage}-gateway-rest-api"
  endpoint_configuration {
    types = ["EDGE"]
  }
}

resource "aws_api_gateway_deployment" "deployment" {
  depends_on  = [
    "aws_api_gateway_integration.get_campaigns_integration"
  ]
  rest_api_id = "${aws_api_gateway_rest_api.gateway_rest_api.id}"
  stage_name  = "${var.stage}"
  stage_description = "${local.combined_hash}"
  description = "Deployed at ${timestamp()}"
}

resource "aws_api_gateway_resource" "items_resource" {
  rest_api_id = "${aws_api_gateway_rest_api.gateway_rest_api.id}"
  parent_id   = "${aws_api_gateway_rest_api.gateway_rest_api.root_resource_id}"
  path_part   = "item"
}