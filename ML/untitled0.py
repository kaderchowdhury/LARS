# -*- coding: utf-8 -*-
"""
Created on Sat Aug 13 12:13:53 2016

@author: kaderchowdhury
"""

# Evaluate using Cross Validation
import pandas
from sklearn import cross_validation
from sklearn.linear_model import LogisticRegression
from sklearn.ensemble import RandomForestClassifier
url = "/Users/kaderchowdhury/University/Project/data_v2/datamodel_encoded_v2.csv"
names = ['id','day', 'time', 'friends', 'activity','feelings','emotion','class'] 
dataframe = pandas.read_csv(url, names=names)
array = dataframe.values
X = array[:,0:7]
Y = array[:,7]
num_folds = 10
num_instances = len(X)
seed = 7
kfold = cross_validation.KFold(n=num_instances, n_folds=num_folds, random_state=seed)
model = RandomForestClassifier(n_estimators = 30)
results = cross_validation.cross_val_score(model, X, Y, cv=kfold)
print("Accuracy: %.3f%% (%.3f%%)") % (results.mean()*100.0, results.std()*100.0)