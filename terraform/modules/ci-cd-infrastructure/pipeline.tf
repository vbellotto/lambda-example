resource "aws_codebuild_project" "codebuild" {
  name = "${var.project_name}-${data.aws_region.current.name}-${var.stage}-codebuild"
  description = "CodeBuild for project ${var.project_name}"
  artifacts {
    type = "CODEPIPELINE"
  }
  environment {
    compute_type = "BUILD_GENERAL1_SMALL"
    image = "aws/codebuild/java:openjdk-8"
    type = "LINUX_CONTAINER"
  }
  source {
    type = "CODEPIPELINE"
    buildspec = "buildspec.yaml"
  }
  service_role = "${aws_iam_role.code_build_role.arn}"
  tags = "${merge(var.tags, map("Name", format("%s", "${var.project_name}-${data.aws_region.current.name}-${var.stage}-codebuild")))}"
}

resource "aws_codepipeline" "pipeline" {
  name = "${var.project_name}-${data.aws_region.current.name}-${var.stage}"
  role_arn = "${aws_iam_role.pipeline_role.arn}"
  artifact_store {
    location = "${var.artifact_bucket_name}"
    type = "S3"
  }
  stage {
    name = "Source"
    action {
      category = "Source"
      owner = "AWS"
      name = "Source"
      provider = "CodeCommit"
      version = "1"
      configuration {
        RepositoryName = "${var.repository}"
        BranchName = "${var.branch}"
      }
      output_artifacts = ["SourceZip"]
    }
  }
  stage {
    name = "Build"
    action {
      category = "Build"
      owner = "AWS"
      name = "Build"
      provider = "CodeBuild"
      version = "1"
      configuration = {
        ProjectName = "${aws_codebuild_project.codebuild.name}"
      }
      input_artifacts = [
        "SourceZip"]
      output_artifacts = [
        "GetItemsLambda"]
    }
  }
  stage {
    name = "DeployApi"
    action {
      category = "Invoke"
      owner = "AWS"
      name = "GetCampaignsLambdaDeploy"
      provider = "Lambda"
      version = "1"
      input_artifacts = [
        "ApiGetCampaignsLambda"]
      configuration = {
        FunctionName = "${aws_lambda_function.auto_deploy_lambda.function_name}"
        UserParameters = "${var.get_items_lambda_name}"
      }
    }
  }
}