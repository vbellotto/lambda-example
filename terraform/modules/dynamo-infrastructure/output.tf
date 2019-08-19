output "dynamo_items_table_name" {
  value = "${aws_dynamodb_table.items_table.name}"
}