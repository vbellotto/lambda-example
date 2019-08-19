locals {
  project_name = "item-example"
  stage = "dev"
  log_level = "Info"
  repository = "ctw-test"
  branch = "master"
}

resource "aws_s3_bucket" "artifact_bucket"{
  bucket        = "${local.project_name}-${data.aws_region.current.name}-${local.stage}-artifact-bucket"
  force_destroy = true
  acl           = "private"
  region        = "${data.aws_region.current.name}"
}

module "ci-cd-infrastructure" {
  source = "../../modules/ci-cd-infrastructure"
  project_name  = "${local.project_name}"
  stage = "${local.stage}"
  repository = "${local.repository}"
  branch = "${local.branch}"
  artifact_bucket_name = "${aws_s3_bucket.artifact_bucket.bucket}"
  log_level = "${local.log_level}"
  get_items_lambda_name = "${local.project_name}-${data.aws_region.current.name}-${local.stage}-get-items"
}

module "dynamo-infrastructure" {
  source = "../../modules/dynamo-infrastructure"
  project_name  = "${local.project_name}"
  stage = "${local.stage}"
}

module "lambda-infrastructure" {
  source = "../../modules/lambda-infrastructure"
  project_name  = "${local.project_name}"
  stage = "${local.stage}"
  log_level = "${local.log_level}"
  artifact_bucket_name = "${aws_s3_bucket.artifact_bucket}"
  items_table = "${module.dynamo-infrastructure.dynamo_items_table_name}"
}