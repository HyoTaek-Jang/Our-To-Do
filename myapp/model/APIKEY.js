class APIKEY {
  static getKey() {
    return process.env.API_KEY;
  }
}

module.exports = APIKEY;
