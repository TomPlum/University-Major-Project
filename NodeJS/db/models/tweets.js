const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const ObjectId = Schema.ObjectId;
const twitter = require('../databases/twitter');

const tweets = new Schema({
    _id: ObjectId,
    tweet_id: String,
    created_at: Date,
    text: String,
    user: String,
    country: String,
    language: String,
    screenName: String
});

module.exports = twitter.model('tweets', tweets, 'tweets');