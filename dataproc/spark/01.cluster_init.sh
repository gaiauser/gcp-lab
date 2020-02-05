gcloud dataproc clusters create spark-batch-01 \
	--region asia-southeast1 \
	--subnet peer-subnet-to-linknet172-22-199-0 \
	--master-machine-type n1-standard-4 \
	--master-boot-disk-size 1024 \
	--num-workers 2 \
	--worker-machine-type n1-standard-4 \
	--worker-boot-disk-size 1024 \
	--image-version 1.2-debian9
