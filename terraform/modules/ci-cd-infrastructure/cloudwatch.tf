resource "aws_sns_topic" "self_service_portal_backend_pipeline_topic" {
  name = "cdh-self-service-backend-pipeline-notification"
}

resource "aws_cloudwatch_event_rule" "code_pipeline_dev_rule" {
  name        = "${var.project_name}-${data.aws_region.current.name}-${var.stage}-pipeline-rule"
  description = "CDH Self-Service-Portal Backend Pipeline DEV Rule"
  event_pattern = <<PATTERN
{
  "source": ["aws.codepipeline"],
  "detail-type": ["CodePipeline Pipeline Dev Execution State Change"],
  "detail": {
    "state": [
      "FAILED"
    ],
    "pipeline": [
      "${aws_codepipeline.pipeline.name}"
    ]
  }
}
PATTERN
}

resource "aws_cloudwatch_event_target" "sns_dev" {
  rule      = "${aws_cloudwatch_event_rule.code_pipeline_dev_rule[count.index].name}"
  target_id = "CDHSelfServicePortalBackendPipelineDevNotification"
  arn       = "${aws_sns_topic.self_service_portal_backend_pipeline_topic.arn}"
}

resource "aws_sns_topic_policy" "default_dev" {
  arn    = "${aws_sns_topic.self_service_portal_backend_pipeline_topic.arn}"
  policy = "${data.aws_iam_policy_document.sns_topic_policy_dev[count.index].json}"
}

data "aws_iam_policy_document" "sns_topic_policy_dev" {
  statement {
    effect  = "Allow"
    actions = ["SNS:Publish"]

    principals {
      type        = "Service"
      identifiers = ["events.amazonaws.com"]
    }

    resources = ["${aws_sns_topic.self_service_portal_backend_pipeline_topic.arn}"]
  }
}