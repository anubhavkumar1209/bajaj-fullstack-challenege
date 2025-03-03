import React, { useState, useEffect } from 'react';
import './style.css'; // Updated import statement

export default function App() {
  const [inputJson, setInputJson] = useState('');
  const [error, setError] = useState('');
  const [response, setResponse] = useState(null);
  const [selectedOptions, setSelectedOptions] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  // Set document title and default options
  useEffect(() => {
    if (response?.roll_number) {
      document.title = response.roll_number;
    }
    if (response?.is_success) {
      setSelectedOptions(['numbers', 'alphabets', 'highest']);
    }
  }, [response]);

  const validateJSON = (jsonString) => {
    try {
      const parsed = JSON.parse(jsonString);
      return parsed.data && Array.isArray(parsed.data);
    } catch {
      return false;
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setResponse(null);

    if (!validateJSON(inputJson)) {
      setError('Invalid JSON format - must contain "data" array');
      return;
    }

    setIsLoading(true);
    try {
      const res = await fetch('http://localhost:8080/bfhl', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: inputJson,
      });

      const data = await res.json();
      
      if (!res.ok || !data.is_success) {
        throw new Error(data.error || 'API request failed');
      }

      setResponse(data);
    } catch (err) {
      setError(err.message);
      setResponse(null);
    } finally {
      setIsLoading(false);
    }
  };

  const handleOptionToggle = (option) => {
    setSelectedOptions(prev => 
      prev.includes(option) 
        ? prev.filter(item => item !== option) 
        : [...prev, option]
    );
  };

  return (
    <div className="container">
      <h1>Data Processor</h1>
      
      <form onSubmit={handleSubmit}>
        <textarea
          value={inputJson}
          onChange={(e) => setInputJson(e.target.value)}
          placeholder={'Example: {"data": ["M", "1", "334", "4", "B"]}'}
          disabled={isLoading}
        />
        <button type="submit" disabled={isLoading}>
          {isLoading ? 'Processing...' : 'Process Data'}
        </button>
      </form>

      {error && <div className="error">{error}</div>}

      {response?.is_success && (
        <div className="response-container">
          <div className="options-panel">
            {['numbers', 'alphabets', 'highest'].map((option) => (
              <label key={option} className="option-label">
                <input
                  type="checkbox"
                  checked={selectedOptions.includes(option)}
                  onChange={() => handleOptionToggle(option)}
                />
                {option.charAt(0).toUpperCase() + option.slice(1)}
              </label>
            ))}
          </div>

          <div className="results">
            {selectedOptions.includes('numbers') && (
              <div className="result-box">
                <h3>Numbers</h3>
                <p>{response.numbers?.join(', ') || 'None found'}</p>
              </div>
            )}

            {selectedOptions.includes('alphabets') && (
              <div className="result-box">
                <h3>Alphabets</h3>
                <p>{response.alphabets?.join(', ') || 'None found'}</p>
              </div>
            )}

            {selectedOptions.includes('highest') && (
              <div className="result-box">
                <h3>Highest Alphabet</h3>
                <p>{response.highest_alphabet?.join(', ') || 'None found'}</p>
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}