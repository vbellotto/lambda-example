resource "aws_iam_role" "code_build_role" {
  name               = "${var.project_name}-${data.aws_region.current.name}-${var.stage}-codebuild-role"
  description        = "Allows CodeBuild to call AWS services"
  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "codebuild.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF
}

data "aws_iam_policy_document" "code_build_role_policy_document" {
  statement {
    sid = "CodeBuildBasePolicy"

    actions = [
      "s3:PutObject",
      "s3:GetObject",
      "s3:GetObjectVersion",
      "s3:GetBucketAcl",
      "s3:GetBucketLocation",
    ]

    resources = [
      "*",
    ]

    effect = "Allow"
  }

  statement {
    sid = "CodeBuildCloudWatchPolicy"

    actions = [
      "logs:CreateLogGroup",
      "logs:CreateLogStream",
      "logs:PutLogEvents",
    ]

    resources = [
      "*",
    ]

    effect = "Allow"
  }
}

resource "aws_iam_role_policy" "code_build_role_policy" {
  name = "${aws_iam_role.code_build_role.name}-policy"
  policy = "${data.aws_iam_policy_document.code_build_role_policy_document.json}"
  role   = "${aws_iam_role.code_build_role.id}"
}

resource "aws_iam_role" "pipeline_role" {
  name               = "${var.project_name}-${data.aws_region.current.name}-${var.stage}-pipeline-role"
  description        = "Allows CodePipeline to call AWS services"
  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "codepipeline.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF
  tags = "${merge(var.tags, map("Name", format("%s", "${var.project_name}-${data.aws_region.current.name}-${var.stage}-pipeline-role")))}"
}

data "aws_iam_policy_document" "codepipeline_policy_document" {
  statement {
    sid = "CodeDeployPolicy"

    actions = [
      "codedeploy:CreateDeployment",
      "codedeploy:GetApplication",
      "codedeploy:GetApplicationRevision",
      "codedeploy:GetDeployment",
      "codedeploy:GetDeploymentConfig",
      "codedeploy:RegisterApplicationRevision",
    ]

    resources = [
      "*",
    ]

    effect = "Allow"
  }

  statement {
    sid = "ServicesPolicy"

    actions = [
      "s3:*",
      "cloudwatch:*",
      "lambda:*",
    ]

    resources = [
      "*",
    ]

    effect = "Allow"
  }

  statement {
    sid = "CodeBuildPolicy"

    actions = [
      "codebuild:BatchGetBuilds",
      "codebuild:StartBuild",
    ]

    resources = [
      "*",
    ]

    effect = "Allow"
  }
}

resource "aws_iam_role_policy" "codepipeline_role_policy" {
  name   = "${aws_iam_role.pipeline_role.name}-policy"
  policy = "${data.aws_iam_policy_document.codepipeline_policy_document.json}"
  role   = "${aws_iam_role.pipeline_role.id}"
}
