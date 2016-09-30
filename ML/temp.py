# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

from __future__ import print_function

import os
import subprocess

import pandas as pd
import numpy as np
from sklearn.tree import DecisionTreeClassifier, export_graphviz
from sklearn.feature_extraction import DictVectorizer
from sklearn.externals.six import StringIO 
#import pydot
from sklearn import tree
from IPython.display import Image 
#from graph_tool.all import *
from sklearn.ensemble import RandomForestClassifier
# Let's make it available in our namespace:
from sklearn.ensemble import RandomForestRegressor
from multiprocessing import dummy as __mp_dummy
import pydot

train = pd.read_csv("/Users/kaderchowdhury/University/Project/data_v2/datamodel_v2_encoded.csv")
test = pd.read_csv("/Users/kaderchowdhury/University/Project/data_v2/testdata_v2_encoded.csv")

def load_data(filepath):

    if os.path.exists(filepath):
        print("-- datamodel_v2.csv found locally")
        df = pd.read_csv(filepath, index_col=0)
    else:
        print("-- dataset not found")
        
        #with open("/Users/kaderchowdhury/University/Project/data/datamodel_v2.csv", 'w') as f:
        #    print("-- writing to local datamodel_v2.csv file")
        #    df.to_csv(f)
        
    return df

df = load_data("/Users/kaderchowdhury/University/Project/data/datamodel_v2.csv")

print("* place types:", df["class"].unique(), sep="\n")

def encode_target(df):
    """Add column to df with integers for the target.

    Args
    ----
    df -- pandas DataFrame.
    target_column -- column to map to int, producing
                     new Target column.

    Returns
    -------
    df_mod -- modified DataFrame.
    targets -- list of target names.
    """
    df_mod = df.copy()
    targets = df_mod["class"].unique()
    map_to_int = {name: n for n, name in enumerate(targets)}
    df_mod["class_mod"] = df_mod["class"].replace(map_to_int)

    targets = df_mod["day"].unique()
    map_to_int = {name: n for n, name in enumerate(targets)}
    df_mod["day_mod"] = df_mod["day"].replace(map_to_int)
    
    targets = df_mod["time"].unique()
    map_to_int = {name: n for n, name in enumerate(targets)}
    df_mod["time_mod"] = df_mod["time"].replace(map_to_int)
    
    targets = df_mod["friends"].unique()
    map_to_int = {name: n for n, name in enumerate(targets)}
    df_mod["friends_mod"] = df_mod["friends"].replace(map_to_int)

    targets = df_mod["activity"].unique()
    map_to_int = {name: n for n, name in enumerate(targets)}
    df_mod["activity_mod"] = df_mod["activity"].replace(map_to_int)

    targets = df_mod["feelings"].unique()
    map_to_int = {name: n for n, name in enumerate(targets)}
    df_mod["feelings_mod"] = df_mod["feelings"].replace(map_to_int)

    targets = df_mod["emotion"].unique()
    map_to_int = {name: n for n, name in enumerate(targets)}
    df_mod["emotion_mod"] = df_mod["emotion"].replace(map_to_int)    
    return df_mod

df2 = encode_target(df);
print("* df.head()", df2.head(), sep="\n", end="\n\n")
#print("* df.tail()", df2.tail(), sep="\n", end="\n\n")
#print("* df2.head()", df2[["class_mod", "class"]].head(),
#      sep="\n", end="\n\n")
#print("* df2.tail()", df2[["Target", "class"]].tail(),
 #     sep="\n", end="\n\n")      
#print("* targets", targets, sep="\n", end="\n\n")

features = list(df2.columns[8:])
print("* features:", features, sep="\n")
#print("* df2.head()", df2.columns[:6],
#      sep="\n", end="\n\n")
y = df2["class_mod"]
X = df2[features]
#clf = DecisionTreeClassifier(min_samples_split=20, random_state=99)
#clf = RandomForestClassifier(n_estimators = 30);
#clf.fit(X, y)
#export LC_ALL=en_US.UTF-8
#export LANG=en_US.UTF-8
#
#testData = load_data("/Users/kaderchowdhury/University/Project/data/input_data.csv")
#td = encode_target(testData)
print(train.head())
cols = ['day', 'time', 'friends', 'activity','feelings','emotion'] 
colsRes = ['class']

trainArr = train.as_matrix(cols)
trainRes = train.as_matrix(colsRes)

rf = RandomForestClassifier(n_estimators=30) # initialize
rf.fit(trainArr, trainRes) # fit the data to the algorithm

testArr = test.as_matrix(cols)
results = rf.predict(testArr)

file=open("/Users/kaderchowdhury/University/Project/data_v2/prediction.txt",'w')
test['predictions'] = results
for item in test['predictions']:
    file.write('%s' %item)
    file.write("\n")
file.close()
#print(test_features)
#results = clf.predict(test_features)
#print(results)
def visualize_tree(clf, f_names):
    """Create tree png using graphviz.

    Args
    ----
    tree -- scikit-learn DecsisionTree.
    feature_names -- list of feature names.
    """
    with open("dt.dot", 'w') as f:
        f = export_graphviz(clf, out_file=f)

    command = ["dot", "-Tpng", "dt.dot", "-o", "dt.png"]
    try:
        subprocess.check_call(command)
    except:
        exit("Could not run dot, ie graphviz, to "
             "produce visualization")


#visualize_tree(clf, features)

#export_graphviz(clf, out_file='tree.dot') #produces dot file

#dotfile = StringIO()
#export_graphviz(clf, out_file=dotfile)
#pydot.graph_from_dot_data(dotfile.getvalue()).write_png("dtree2.png")