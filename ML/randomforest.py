# -*- coding: utf-8 -*-
"""
Created on Tue Jul 26 17:32:00 2016

@author: kaderchowdhury
"""

import pandas as pd
from sklearn.ensemble import RandomForestClassifier
train = pd.read_csv("/Users/kaderchowdhury/University/Project/data/datamodel_v2_encoded.csv")
test = pd.read_csv("/Users/kaderchowdhury/University/Project/data/input_data_encoded.csv")

print(train.head())
cols = ['day', 'time', 'friends', 'activity','feelings','emotion'] 
colsRes = ['class']

trainArr = train.as_matrix(cols)
trainRes = train.as_matrix(colsRes)

rf = RandomForestClassifier(n_estimators=100) # initialize
rf.fit(trainArr, trainRes) # fit the data to the algorithm

testArr = test.as_matrix(cols)
results = rf.predict(testArr)

test['predictions'] = results
print(test)