#!/usr/bin/env bash

export VW_BIN=/opt/vw/vowpalwabbit

TRAIN="cat ../data/train.csv"
TEST="cat ../data/test.csv"
JAVA_BIN="java -Xmx4g -cp ../display-ad-java/target/*:. com.sigaphi.kaggle.displayad"

echo "import data into redis ..."
$TRAIN | $JAVA_BIN.ToRedis
$TEST | $JAVA_BIN.ToRedis

echo "making vw input files ..."
$TRAIN | $JAVA_BIN.FeaturesToVw | gzip > train.vw.gz
$TEST | $JAVA_BIN.FeaturesToVw | gzip > test.vw.gz

echo "training model 1 ..."
python ../scripts/vw_run.py quad_11 3 6000000
python ../scripts/vw_run.py quad_13 3 100000
python ../scripts/vw_run.py quad_12 1 10000
mv prediction_test.txt prediction_test_1.txt

echo "training model 2 ..."
python ../scripts/vw_run.py poly_1 6 1
mv prediction_test.txt prediction_test_2.txt

echo "training model 3 ..."
python ../scripts/vw_run.py poly_2 6 10000
mv prediction_test.txt prediction_test_3.txt

echo "training model 4 ..."
python ../scripts/vw_run.py poly_3 6 100000
mv prediction_test.txt prediction_test_4.txt

echo "making a submission file"
cat <(echo "Id,p1,p2,p3,p4") <(paste -d"," <(zcat test.vw.gz | cut -f1 | cut -d"," -f1) prediction_test_1.txt prediction_test_2.txt prediction_test_3.txt prediction_test_4.txt) | python ../scripts/submit.py