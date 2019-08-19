locals {
  get-items-file = "../../../src/window-opener-1.0-SNAPSHOT.jar"
  get-items-key = "get-items-lambda-${urlencode(base64sha256(file(local.get-items-file)))}"
}

resource "aws_s3_bucket_object" "get_items" {
  bucket = "${var.artifact_bucket_name}"
  key = "${local.get-items-key}"
  source = "${local.get-items-file}"
}

resource "aws_lambda_function" "get_powi_lambda" {
  description = "Receives resources requests.."
  function_name = "${var.project_name}-${data.aws_region.current.name}-${var.stage}-get-items"
  handler = "com.bmw.window.opener.handler.ListNearestPOWIHandler::handleRequest"
  role = "${aws_iam_role.get_items_role.arn}"
  runtime = "java8"
  memory_size = 512
  timeout = 300
  s3_bucket = "${aws_s3_bucket_object.get_items.bucket}"
  s3_key = "${aws_s3_bucket_object.get_items.key}"
  source_code_hash = "${base64sha256(file(aws_s3_bucket_object.get_items.source))}"

  environment {
    variables {
      REGION = "${data.aws_region.current.name}"
      LOG_LEVEL = "${var.log_level}",
      TABLE = "${var.items_table}"
    }
  }
  tags = "${merge(var.tags, map("Name", format("%s", "${var.project_name}-${data.aws_region.current.name}-${var.stage}-get-items")))}"
}

resource "aws_iam_role" "get_items_role" {
  name = "${var.project_name}-${data.aws_region.current.name}-${var.stage}-items-lambda-role"
  description = "Allows Lambda to call AWS services"
  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF
}

resource "aws_iam_role_policy_attachment" "get_powi_execution_role" {
  role = "${aws_iam_role.get_items_role.name}"
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}
resource "aws_iam_role_policy_attachment" "vss_lambda_dynamodb" {
  role       = "${aws_iam_role.get_items_role.name}"
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/AmazonDynamoDBFullAccess"
}