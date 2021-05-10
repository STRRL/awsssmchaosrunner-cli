# awsssmchaosrunner-cli
A simple wrapper for awsssmchaosrunner, make it runs easier.

## Usage

Using that tool with localstack:

You could start up  localstack in docker: 

```shell
docker run --rm -it -p 4566:4566 -p 4571:4571 localstack/localstack
```

Then run an example chaos, remember to replace the `<localstack>` to ths IP of localstack container:

```shell
docker run --rm \
    -v $PWD/example:/example \
    -e AWS_ACCESS_KEY_ID=test \
    -e AWS_SECRET_ACCESS_KEY=test \
    strrl/awsssmchaosrunner-cli:latest \
    attack \
    --region=us-east-1a \
    --endpoint-url=http://<localstack>:4566 \
    -c /example/example-cpu-hog.json
```

> Note: here are some issues with documents on localstack, like [#981](https://github.com/localstack/localstack/issues/981), it returns the bad datetime in response. I'm going to contact upstream for a solution.
