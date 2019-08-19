data "aws_region" "current" {}
data "aws_partition" "current" {}
data "aws_caller_identity" "current" {}

data "aws_lambda_function" "get_items_lambda" {
  function_name = "${var.get_items_lambda_name}"
}