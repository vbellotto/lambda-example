resource "aws_s3_bucket_object" "deploy_lambda_object" {
  bucket = "${var.artifact_bucket_name}"
  key = "auto-deploy-lambda"
  source = "${data.local_file.deploy_lambda.filename}"
}

resource "aws_lambda_function" "auto_deploy_lambda" {
  description = "Lambda function used for deployment."
  function_name = "${var.project_name}-deploy-lambda-${data.aws_region.current.name}-${var.stage}"
  handler = "index.handler"
  role = "${aws_iam_role.lambda_deploy_role.arn}"
  runtime = "nodejs8.10"
  memory_size = 1536
  timeout = 120
  s3_bucket = "${aws_s3_bucket_object.deploy_lambda_object.bucket}"
  s3_key = "${aws_s3_bucket_object.deploy_lambda_object.key}"
  s3_object_version = "${aws_s3_bucket_object.deploy_lambda_object.version_id}"
}

resource "aws_iam_role" "lambda_deploy_role" {
  name               = "${var.project_name}-lambda-deploy-role-${data.aws_region.current.name}-${var.stage}"
  description        = "Allows Lambda to call AWS services"
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
resource "aws_iam_role_policy_attachment" "deploy_lambda_attach" {
  role       = "${aws_iam_role.lambda_deploy_role.name}"
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/AWSLambdaFullAccess"
}
resource "aws_iam_role_policy_attachment" "deploy_lambda_s3" {
  role       = "${aws_iam_role.lambda_deploy_role.name}"
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/AmazonS3FullAccess"
}
resource "aws_iam_role_policy_attachment" "deploy_lambda_code_deploy" {
  role       = "${aws_iam_role.lambda_deploy_role.name}"
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/service-role/AWSCodeDeployRoleForLambda"
}
resource "aws_iam_role_policy_attachment" "deploy_lambda_pipeline" {
  role       = "${aws_iam_role.lambda_deploy_role.name}"
  policy_arn = "arn:${data.aws_partition.current.partition}:iam::aws:policy/AWSCodePipelineFullAccess"
}